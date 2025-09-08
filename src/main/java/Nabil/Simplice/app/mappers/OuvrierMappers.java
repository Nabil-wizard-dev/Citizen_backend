package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.dto.request.OuvrierRequest;
import Nabil.Simplice.app.dto.request.RegisterRequest;
import Nabil.Simplice.app.dto.response.OuvrierResponse;
import Nabil.Simplice.app.dto.response.RegisterResponse;
import Nabil.Simplice.app.entity.Ouvrier;
import Nabil.Simplice.app.entity.Utilisateur;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OuvrierMappers extends UtilisateurMappers{

    public OuvrierMappers(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }
    
    //toEntity pour OuvrierRequest vers Ouvrier
    public Ouvrier toEntity(OuvrierRequest ouvrierReq) {
        Ouvrier ouvrier = new Ouvrier();
        ouvrier.setTrackingId(UUID.randomUUID());
        ouvrier.setCni(ouvrierReq.getCni());
        ouvrier.setEmail(ouvrierReq.getEmail());
        ouvrier.setMotDePasse(passwordEncoder.encode(ouvrierReq.getMotDePasse()));
        ouvrier.setNom(ouvrierReq.getNom());
        ouvrier.setRole(ouvrierReq.getRole());
        ouvrier.setPrenom(ouvrierReq.getPrenom());
        ouvrier.setNumero(ouvrierReq.getNumero());
        ouvrier.setDateNaissance(ouvrierReq.getDateNaissance());
        ouvrier.setAdresse(ouvrierReq.getAdresse());
        
        // Attributs spécifiques à l'ouvrier
        ouvrier.setSignalementActuelId(ouvrierReq.getSignalementActuelId());
        ouvrier.setSpecialite(ouvrierReq.getSpecialite());
        ouvrier.setServiceId(ouvrierReq.getServiceId());
        
        return ouvrier;
    }
    
    //toResponse pour Ouvrier vers OuvrierResponse
    public OuvrierResponse toResponse(Ouvrier ouvrier) {
        OuvrierResponse ouvrierRes = new OuvrierResponse();
        ouvrierRes.setTrackingId(ouvrier.getTrackingId());
        ouvrierRes.setCni(ouvrier.getCni());
        ouvrierRes.setDateNaissance(ouvrier.getDateNaissance());
        ouvrierRes.setEmail(ouvrier.getEmail());
        ouvrierRes.setNom(ouvrier.getNom());
        ouvrierRes.setPrenom(ouvrier.getPrenom());
        ouvrierRes.setNumero(ouvrier.getNumero());
        ouvrierRes.setRole(ouvrier.getRole());
        ouvrierRes.setAdresse(ouvrier.getAdresse());
        
        // Attributs spécifiques à l'ouvrier
        ouvrierRes.setSignalementActuelId(ouvrier.getSignalementActuelId());
        ouvrierRes.setSpecialite(ouvrier.getSpecialite());
        ouvrierRes.setServiceId(ouvrier.getServiceId());
        
        return ouvrierRes;
    }
    
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
