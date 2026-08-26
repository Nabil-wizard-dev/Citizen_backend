package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.entity.Notification;
import Nabil.Simplice.app.entity.Utilisateur;
import Nabil.Simplice.app.enums.TypeNotification;
import Nabil.Simplice.app.enums.UtilisateurRole;
import Nabil.Simplice.app.repository.NotificationRepository;
import Nabil.Simplice.app.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Point d'emission unique des notifications declenchees par le metier.
 *
 * Deux garanties tenues ici, et nulle part ailleurs :
 *
 * 1. Une notification qui echoue ne fait jamais echouer l'action metier. Toutes les
 *    methodes avalent leurs exceptions : un signalement reste cree meme si l'insertion
 *    de la notification tombe.
 * 2. REQUIRES_NEW ouvre une transaction distincte. Sans cela une erreur d'insertion
 *    marquerait la transaction appelante rollback-only, et le signalement serait perdu
 *    malgre le catch.
 */
@Component
public class NotificationPublisher {

    private static final Logger logger = LoggerFactory.getLogger(NotificationPublisher.class);

    private final NotificationRepository notificationRepository;
    private final UtilisateurRepository utilisateurRepository;

    public NotificationPublisher(NotificationRepository notificationRepository,
                                 UtilisateurRepository utilisateurRepository) {
        this.notificationRepository = notificationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    /** Notifie un destinataire unique. Un destinataire null est ignore sans bruit. */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifier(UUID destinataireId, TypeNotification type, UUID entiteId, String message) {
        if (destinataireId == null || message == null || message.trim().isEmpty()) {
            return;
        }
        try {
            notificationRepository.save(construire(destinataireId, type, entiteId, message));
        } catch (Exception e) {
            logger.error("Notification non enregistree pour {} : {}", destinataireId, message, e);
        }
    }

    /**
     * Notifie l'auteur d'une entite auditee.
     *
     * createBy contient le trackingId de l'auteur sous forme de chaine (voir AuditConfiguration) ;
     * il est vide pour les donnees creees hors contexte authentifie, d'ou le parsing defensif.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifierAuteur(String createBy, TypeNotification type, UUID entiteId, String message) {
        UUID destinataireId = versUuid(createBy);
        if (destinataireId == null) {
            logger.debug("Aucun auteur exploitable ({}) : notification ignoree", createBy);
            return;
        }
        notifierInterne(destinataireId, type, entiteId, message);
    }

    /** Diffuse le meme message a tous les citoyens. Insertion en lot : une seule requete. */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifierTousLesCitoyens(TypeNotification type, UUID entiteId, String message) {
        notifierRole(UtilisateurRole.CITOYEN, type, entiteId, message);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifierRole(UtilisateurRole role, TypeNotification type, UUID entiteId, String message) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        try {
            List<Notification> notifications = utilisateurRepository.findByRole(role)
                    .stream()
                    .map(Utilisateur::getTrackingId)
                    .filter(trackingId -> trackingId != null)
                    .map(trackingId -> construire(trackingId, type, entiteId, message))
                    .collect(Collectors.toList());

            if (!notifications.isEmpty()) {
                notificationRepository.saveAll(notifications);
            }
        } catch (Exception e) {
            logger.error("Diffusion de notification echouee pour le role {} : {}", role, message, e);
        }
    }

    /** Variante interne : deja dans la transaction ouverte par la methode publique appelante. */
    private void notifierInterne(UUID destinataireId, TypeNotification type, UUID entiteId, String message) {
        try {
            notificationRepository.save(construire(destinataireId, type, entiteId, message));
        } catch (Exception e) {
            logger.error("Notification non enregistree pour {} : {}", destinataireId, message, e);
        }
    }

    private Notification construire(UUID destinataireId, TypeNotification type, UUID entiteId, String message) {
        Notification notification = new Notification();
        notification.setTrackingId(UUID.randomUUID());
        notification.setDestinataireId(destinataireId);
        notification.setEntiteId(entiteId);
        notification.setType(type);
        notification.setMessage(message);
        notification.setLu(false);
        return notification;
    }

    private UUID versUuid(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(valeur.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
