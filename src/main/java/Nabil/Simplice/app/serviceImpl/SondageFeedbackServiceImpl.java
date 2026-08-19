package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.SondageFeedbackRequest;
import Nabil.Simplice.app.dto.response.SondageFeedbackResponse;
import Nabil.Simplice.app.dto.response.SondageStatistiqueResponse;
import Nabil.Simplice.app.entity.Sondage;
import Nabil.Simplice.app.entity.SondageFeedback;
import Nabil.Simplice.app.entity.Utilisateur;
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
    public ResponseEntity<ApiResponse<SondageStatistiqueResponse>> getStatistiques(UUID sondageId) {
        try {
            if (!sondageRepository.existsSondageByTrackingId(sondageId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + sondageId));
            }

            long total = repository.countBySondageTrackingId(sondageId);
            long confirmations = repository.countBySondageTrackingIdAndConfirmationTrue(sondageId);
            Double moyenne = repository.moyenneNoteBySondage(sondageId);

            // aucune reponse : on renvoie des zeros plutot qu'un null, le client n'a rien a gerer
            double moyenneArrondie = (moyenne == null) ? 0d : Math.round(moyenne * 10d) / 10d;
            double taux = (total == 0) ? 0d : Math.round((confirmations * 1000d) / total) / 10d;

            SondageStatistiqueResponse statistiques =
                    new SondageStatistiqueResponse(sondageId, moyenneArrondie, total, confirmations, taux);
            return ResponseEntity.ok(ApiResponse.success(statistiques, "Statistiques calculées avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors du calcul des statistiques du sondage {}", sondageId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors du calcul des statistiques"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> addFeedback(SondageFeedbackRequest request) {
        try {
            Utilisateur auteur = utilisateurConnecte();
            if (auteur == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Utilisateur non authentifié"));
            }

            Optional<Sondage> sondage = sondageRepository.findByTrackingId(request.getSondageId());
            if (sondage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + request.getSondageId()));
            }

            // un utilisateur ne repond qu'une fois : evite les doublons qui fausseraient la moyenne
            if (repository.existsBySondageTrackingIdAndUtilisateurTrackingId(
                    request.getSondageId(), auteur.getTrackingId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Vous avez déjà répondu à ce sondage"));
            }

            SondageFeedback saved = repository.save(mappers.toEntity(request, sondage.get(), auteur));
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
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> updateFeedback(UUID id, SondageFeedbackRequest request) {
        try {
            Utilisateur auteur = utilisateurConnecte();
            if (auteur == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Utilisateur non authentifié"));
            }

            Optional<SondageFeedback> existant = repository.findByTrackingId(id);
            if (existant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Réponse non trouvée avec l'ID : " + id));
            }

            SondageFeedback feedback = existant.get();

            // seul l'auteur modifie sa reponse : une mairie ne peut pas réécrire les avis recus
            if (!feedback.getUtilisateur().getTrackingId().equals(auteur.getTrackingId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Vous ne pouvez modifier que votre propre réponse"));
            }

            // le sondage et l'auteur ne changent jamais : seul le contenu de la reponse est modifiable
            if (request.getNote() != null) {
                feedback.setNote(request.getNote());
            }
            if (request.getCommentaire() != null) {
                feedback.setCommentaire(request.getCommentaire());
            }
            if (request.getConfirmation() != null) {
                feedback.setConfirmation(request.getConfirmation());
            }

            SondageFeedback updated = repository.save(feedback);
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(updated), "Mise à jour réussie"));
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la réponse {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la mise à jour de la réponse"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> deleteFeedback(UUID id) {
        try {
            Utilisateur auteur = utilisateurConnecte();
            if (auteur == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Utilisateur non authentifié"));
            }

            Optional<SondageFeedback> existant = repository.findByTrackingId(id);
            if (existant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Réponse non trouvée avec l'ID : " + id));
            }

            SondageFeedback feedback = existant.get();

            if (!feedback.getUtilisateur().getTrackingId().equals(auteur.getTrackingId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Vous ne pouvez supprimer que votre propre réponse"));
            }

            repository.delete(feedback);
            return ResponseEntity.ok(ApiResponse.success(null, "Réponse supprimée avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la réponse {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la suppression de la réponse"));
        }
    }

    /**
     * Utilisateur porte par le token courant.
     * Le filtre JWT place l'email dans le contexte de securite : on repart de la.
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
