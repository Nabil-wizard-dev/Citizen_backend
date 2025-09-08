package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.dto.request.AnnonceRequest;
import Nabil.Simplice.app.dto.response.AnnonceResponse;
import Nabil.Simplice.app.entity.Annonce;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AnnonceMappers {
    public Annonce toEntity(AnnonceRequest req){
        Annonce annonce = new Annonce();
        annonce.setTrackingId(UUID.randomUUID());
        annonce.setMessage(req.getMessage());
        annonce.setFichiersPaths(req.getFichierPaths());
        annonce.setEntiteId(req.getEntiteId());
        return annonce;
    }

    public AnnonceResponse toResponse(Annonce annonce){
        AnnonceResponse response = new AnnonceResponse();
        response.setTrackingId(annonce.getTrackingId());
        response.setMessage(annonce.getMessage());
        response.setEntiteId(annonce.getEntiteId());
        response.setFichierPaths(annonce.getFichiersPaths());
        return response;
    }
}
