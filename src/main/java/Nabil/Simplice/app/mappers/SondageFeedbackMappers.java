package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.dto.request.SondageFeedbackRequest;
import Nabil.Simplice.app.dto.response.SondageFeedbackResponse;
import Nabil.Simplice.app.entity.Sondage;
import Nabil.Simplice.app.entity.SondageFeedback;
import Nabil.Simplice.app.entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SondageFeedbackMappers {

    /**
     * Le sondage et l'auteur ne viennent pas de la requete : ils sont resolus par le service
     * (le sondage depuis son trackingId, l'auteur depuis le token).
     */
    public SondageFeedback toEntity(SondageFeedbackRequest request, Sondage sondage, Utilisateur utilisateur) {
        SondageFeedback feedback = new SondageFeedback();
        feedback.setTrackingId(UUID.randomUUID());
        feedback.setNote(request.getNote());
        feedback.setCommentaire(request.getCommentaire());
        feedback.setConfirmation(request.getConfirmation());
        feedback.setSondage(sondage);
        feedback.setUtilisateur(utilisateur);
        feedback.setDateReponse(LocalDateTime.now());
        return feedback;
    }

    public SondageFeedbackResponse toResponse(SondageFeedback feedback) {
        SondageFeedbackResponse response = new SondageFeedbackResponse();
        response.setTrackingId(feedback.getTrackingId());
        response.setNote(feedback.getNote());
        response.setCommentaire(feedback.getCommentaire());
        response.setConfirmation(feedback.getConfirmation());
        response.setDateReponse(feedback.getDateReponse());

        if (feedback.getSondage() != null) {
            response.setSondageId(feedback.getSondage().getTrackingId());
        }

        if (feedback.getUtilisateur() != null) {
            Utilisateur auteur = feedback.getUtilisateur();
            response.setAuteurId(auteur.getTrackingId());
            response.setAuteurNom(auteur.getNom());
            response.setAuteurPrenom(auteur.getPrenom());
        }

        return response;
    }
}
