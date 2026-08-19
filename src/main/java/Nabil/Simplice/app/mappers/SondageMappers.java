package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.AnnonceResponse;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.entity.Annonce;
import Nabil.Simplice.app.entity.Sondage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SondageMappers {

    public Sondage toEntity(SondageRequest request){
        Sondage sondage = new Sondage();
        sondage.setTrackingId(UUID.randomUUID());
        sondage.setConfirmation(request.isConfirmation());
        sondage.setCommentaire(request.getCommentaire());
        sondage.setEntiteId(request.getEntiteId());
        sondage.setNote(request.getNote());
        return sondage;

    }


    public SondageResponse toResponse(Sondage request){
        SondageResponse response = new SondageResponse();
        response.setTrackingId(request.getTrackingId());
        response.setCommentaire(request.getCommentaire());
        response.setEntiteId(request.getEntiteId());
        response.setNote(request.getNote());
        response.setConfirmation(request.isConfirmation());
        return response;
    }
}
