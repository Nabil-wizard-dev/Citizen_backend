package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.SondageFeedbackRequest;
import Nabil.Simplice.app.dto.response.SondageFeedbackResponse;
import Nabil.Simplice.app.entity.Sondage;
import Nabil.Simplice.app.entity.SondageFeedback;
import Nabil.Simplice.app.entity.Utilisateur;
import Nabil.Simplice.app.enums.StatutSondage;
import Nabil.Simplice.app.mappers.SondageFeedbackMappers;
import Nabil.Simplice.app.repository.SondageFeedbackRepository;
import Nabil.Simplice.app.repository.SondageRepository;
import Nabil.Simplice.app.repository.UtilisateurRepository;
import Nabil.Simplice.app.service.SondageFeedbackService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SondageFeedbackServiceImpl implements SondageFeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(SondageFeedbackServiceImpl.class);

    private final SondageFeedbackRepository repository;
    private final SondageFeedbackMappers mappers;
    private final SondageRepository sondageRepository;
    private final UtilisateurRepository utilisateurRepository;

    public SondageFeedbackServiceImpl(SondageFeedbackRepository repository,
                                      SondageFeedbackMappers mappers,
                                      SondageRepository sondageRepository,
                                      UtilisateurRepository utilisateurRepository) {
        this.repository = repository;
        this.mappers = mappers;
        this.sondageRepository = sondageRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<List<SondageFeedbackResponse>>> getAllFeedbacks() {
        try {
            List<SondageFeedbackResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(all, "Réponses récupérées avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des réponses", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération des réponses"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<SondageFeedbackResponse>>> getFeedbacksBySondage(UUID sondageId) {
        try {
            if (!sondageRepository.existsSondageByTrackingId(sondageId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + sondageId));
            }

            List<SondageFeedbackResponse> all = repository.findBySondageTrackingId(sondageId)
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(all, "Réponses du sondage récupérées avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des réponses du sondage {}", sondageId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération des réponses"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> getFeedbackById(UUID id) {
        try {
            Optional<SondageFeedback> feedback = repository.findByTrackingId(id);
            if (feedback.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Réponse non trouvée avec l'ID : " + id));
            }
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(feedback.get()),
                    "Réponse récupérée avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de la réponse {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération de la réponse"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> getMaReponse(UUID sondageId) {
        try {
            Utilisateur citoyen = utilisateurConnecte();
            if (citoyen == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Utilisateur non authentifié"));
            }

            if (!sondageRepository.existsSondageByTrackingId(sondageId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + sondageId));
            }

            Optional<SondageFeedback> feedback =
                    repository.findBySondageEtCitoyen(sondageId, citoyen.getTrackingId());

            // pas de 404 ici : "je n'ai pas encore repondu" est une reponse normale pour le front
            if (feedback.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.success(null, "Vous n'avez pas encore répondu à ce sondage"));
            }

            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(feedback.get()),
                    "Votre réponse a été récupérée avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de la réponse au sondage {}", sondageId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération de votre réponse"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> addFeedback(SondageFeedbackRequest request) {
        try {
            Utilisateur citoyen = utilisateurConnecte();
            if (citoyen == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Utilisateur non authentifié"));
            }

            Optional<Sondage> sondageOpt = sondageRepository.findByTrackingId(request.getSondageId());
            if (sondageOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + request.getSondageId()));
            }

            Sondage sondage = sondageOpt.get();

            if (sondage.getStatut() != StatutSondage.OUVERT) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Ce sondage est clôturé : il n'accepte plus de réponse"));
            }

            // un citoyen ne repond qu'une fois : evite les doublons qui fausseraient les compteurs
            if (repository.existsBySondageTrackingIdAndCitoyenTrackingId(
                    request.getSondageId(), citoyen.getTrackingId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Vous avez déjà répondu à ce sondage"));
            }

            SondageFeedback saved = repository.save(mappers.toEntity(request, sondage, citoyen));

            incrementerCompteurs(sondage, saved.getConfirmation(), saved.getNote());
            sondageRepository.save(sondage);

            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(saved),
                    "Réponse enregistrée avec succès"));
        } catch (DataIntegrityViolationException e) {
            // filet de securite si deux requetes arrivent en meme temps : la contrainte d'unicite tranche
            logger.warn("Tentative de double réponse au sondage {}", request.getSondageId(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("Vous avez déjà répondu à ce sondage"));
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement de la réponse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de l'enregistrement de la réponse"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> updateFeedback(UUID id, SondageFeedbackRequest request) {
        try {
            Utilisateur citoyen = utilisateurConnecte();
            if (citoyen == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Utilisateur non authentifié"));
            }

            Optional<SondageFeedback> existant = repository.findByTrackingId(id);
            if (existant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Réponse non trouvée avec l'ID : " + id));
            }

            SondageFeedback feedback = existant.get();

            // seul l'auteur modifie sa reponse : une mairie ne peut pas réécrire les avis reçus
            if (!feedback.getCitoyen().getTrackingId().equals(citoyen.getTrackingId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Vous ne pouvez modifier que votre propre réponse"));
            }

            Sondage sondage = feedback.getSondage();
            if (sondage.getStatut() != StatutSondage.OUVERT) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Ce sondage est clôturé : votre réponse ne peut plus être modifiée"));
            }

            // on retire l'ancienne contribution des compteurs avant d'appliquer la nouvelle
            decrementerCompteurs(sondage, feedback.getConfirmation(), feedback.getNote());

            if (request.getConfirmation() != null) {
                feedback.setConfirmation(request.getConfirmation());
            }
            if (request.getNote() != null) {
                feedback.setNote(request.getNote());
            }
            if (request.getCommentaire() != null) {
                feedback.setCommentaire(request.getCommentaire());
            }

            incrementerCompteurs(sondage, feedback.getConfirmation(), feedback.getNote());

            SondageFeedback updated = repository.save(feedback);
            sondageRepository.save(sondage);

            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(updated), "Mise à jour réussie"));
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la réponse {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la mise à jour de la réponse"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Void>> deleteFeedback(UUID id) {
        try {
            Utilisateur citoyen = utilisateurConnecte();
            if (citoyen == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Utilisateur non authentifié"));
            }

            Optional<SondageFeedback> existant = repository.findByTrackingId(id);
            if (existant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Réponse non trouvée avec l'ID : " + id));
            }

            SondageFeedback feedback = existant.get();

            if (!feedback.getCitoyen().getTrackingId().equals(citoyen.getTrackingId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Vous ne pouvez supprimer que votre propre réponse"));
            }

            Sondage sondage = feedback.getSondage();
            if (sondage.getStatut() != StatutSondage.OUVERT) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Ce sondage est clôturé : votre réponse ne peut plus être retirée"));
            }

            decrementerCompteurs(sondage, feedback.getConfirmation(), feedback.getNote());

            repository.delete(feedback);
            sondageRepository.save(sondage);

            return ResponseEntity.ok(ApiResponse.success(null, "Réponse supprimée avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la réponse {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la suppression de la réponse"));
        }
    }

    // ---- maintenance des compteurs denormalises du sondage ----

    private void incrementerCompteurs(Sondage sondage, Boolean confirmation, Integer note) {
        if (Boolean.TRUE.equals(confirmation)) {
            sondage.setNbOui(sondage.getNbOui() + 1);
        } else {
            sondage.setNbNon(sondage.getNbNon() + 1);
        }
        if (note != null) {
            sondage.setNbNotes(sondage.getNbNotes() + 1);
            sondage.setSommeNotes(sondage.getSommeNotes() + note);
        }
    }

    /** Les Math.max evitent qu'un compteur passe sous zero si une derive s'est installee. */
    private void decrementerCompteurs(Sondage sondage, Boolean confirmation, Integer note) {
        if (Boolean.TRUE.equals(confirmation)) {
            sondage.setNbOui(Math.max(0, sondage.getNbOui() - 1));
        } else {
            sondage.setNbNon(Math.max(0, sondage.getNbNon() - 1));
        }
        if (note != null) {
            sondage.setNbNotes(Math.max(0, sondage.getNbNotes() - 1));
            sondage.setSommeNotes(Math.max(0, sondage.getSommeNotes() - note));
        }
    }

    /**
     * Utilisateur porte par le token courant.
     * Le filtre JWT place l'entite Utilisateur dans le contexte de securite : on repart de la.
     * Renvoie null si la requete n'est pas authentifiee.
     */
    private Utilisateur utilisateurConnecte() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return utilisateurRepository.findByEmail(authentication.getName()).orElse(null);
    }
}
