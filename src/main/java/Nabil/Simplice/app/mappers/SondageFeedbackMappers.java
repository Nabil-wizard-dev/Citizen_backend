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
     * Le sondage et le citoyen ne viennent pas de la requete : ils sont resolus par le service
     * (le sondage depuis son trackingId, le citoyen depuis le token).
     */
    public SondageFeedback toEntity(SondageFeedbackRequest request, Sondage sondage, Utilisateur citoyen) {
        SondageFeedback feedback = new SondageFeedback();
        feedback.setTrackingId(UUID.randomUUID());
        feedback.setConfirmation(request.getConfirmation());
        feedback.setNote(request.getNote());
        feedback.setCommentaire(request.getCommentaire());
        feedback.setSondage(sondage);
        feedback.setCitoyen(citoyen);
        feedback.setDateReponse(LocalDateTime.now());
        return feedback;
    }

    public SondageFeedbackResponse toResponse(SondageFeedback feedback) {
        SondageFeedbackResponse response = new SondageFeedbackResponse();
        response.setTrackingId(feedback.getTrackingId());
        response.setConfirmation(feedback.getConfirmation());
        response.setNote(feedback.getNote());
        response.setCommentaire(feedback.getCommentaire());
        response.setDateReponse(feedback.getDateReponse());

        if (feedback.getSondage() != null) {
            response.setSondageId(feedback.getSondage().getTrackingId());
        }

        if (feedback.getCitoyen() != null) {
            Utilisateur citoyen = feedback.getCitoyen();
            response.setCitoyenId(citoyen.getTrackingId());
            response.setCitoyenNom(citoyen.getNom());
            response.setCitoyenPrenom(citoyen.getPrenom());
        }

        return response;
    }
}
