package Nabil.Simplice.app.mappers;

import org.springframework.stereotype.Component;
import Nabil.Simplice.app.entity.FichierJoin;
import Nabil.Simplice.app.dto.request.FichierJoinRequest;
import Nabil.Simplice.app.dto.response.FichierJoinResponse;
import java.time.LocalDateTime;

@Component
public class FichierJoinMappers {

//    public FichierJoin toEntity(FichierJoinRequest request) {
//        FichierJoin fichier = new FichierJoin();
//        fichier.setNom(request.getNom());
//        fichier.setDescription(request.getDescription());
//        fichier.setUrl(request.getUrl());
//        fichier.setExtension(request.getExtension());
//        fichier.setTaille(request.getTaille());
//        fichier.setDateCreation(LocalDateTime.now());
//        return fichier;
//    }
//
//    public FichierJoinResponse toResponse(FichierJoin fichier) {
//        FichierJoinResponse response = new FichierJoinResponse();
//        response.setId(fichier.getId());
//        response.setNom(fichier.getNom());
//        response.setDescription(fichier.getDescription());
//        response.setUrl(fichier.getUrl());
//        response.setExtension(fichier.getExtension());
//        response.setTaille(fichier.getTaille());
//        response.setDateCreation(fichier.getDateCreation());
//
//        if (fichier.getSignalement() != null) {
//            response.setSignalementId(fichier.getSignalement().getId());
//        }
//
//        return response;
//    }
//
//    public void updateEntityFromRequest(FichierJoin fichier, FichierJoinRequest request) {
//        fichier.setNom(request.getNom());
//        fichier.setDescription(request.getDescription());
//        fichier.setUrl(request.getUrl());
//        fichier.setExtension(request.getExtension());
//        fichier.setTaille(request.getTaille());
//    }
} 