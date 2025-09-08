package Nabil.Simplice.app.mappers;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import Nabil.Simplice.app.dto.request.SignalementRequest;
import Nabil.Simplice.app.dto.response.SignalementResponse;
import Nabil.Simplice.app.entity.FichierJoin;
import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.enums.StatutSignalement;
import Nabil.Simplice.app.repository.AutoriteRepository;
import Nabil.Simplice.app.repository.FichierJoinRepository;
import Nabil.Simplice.app.repository.GeolocalisationRepository;
import Nabil.Simplice.app.repository.OuvrierRepository;
import Nabil.Simplice.app.repository.UtilisateurRepository;

@Component
public class SignalementMappers {
    private final GeolocalisationRepository geolocalisationRepository;
    private final FichierJoinRepository fichierJoinRepository;
    private final GeolocalisationMappers geolocalisationMappers;
    private final FichierJoinMappers fichierJoinMappers;
    private final UtilisateurRepository utilisateurRepository;
    private final OuvrierRepository ouvrierRepository;
    private final AutoriteRepository autoriteRepository;

    public SignalementMappers(
            GeolocalisationRepository geolocalisationRepository,
            FichierJoinRepository fichierJoinRepository,
            GeolocalisationMappers geolocalisationMappers,
            FichierJoinMappers fichierJoinMappers,
            UtilisateurRepository utilisateurRepository,
            OuvrierRepository ouvrierRepository,
            AutoriteRepository autoriteRepository) {
        this.geolocalisationRepository = geolocalisationRepository;
        this.fichierJoinRepository = fichierJoinRepository;
        this.geolocalisationMappers = geolocalisationMappers;
        this.fichierJoinMappers = fichierJoinMappers;
        this.utilisateurRepository = utilisateurRepository;
        this.ouvrierRepository = ouvrierRepository;
        this.autoriteRepository = autoriteRepository;
    }

    public Signalement toEntity(SignalementRequest req) {
        Signalement signalement = new Signalement();
        signalement.setTrackingId(UUID.randomUUID());
        signalement.setTitre(req.getTitre());
        signalement.setCode(req.getCode());
        signalement.setDescription(req.getDescription());
        signalement.setTypeService(req.getTypeService());
        signalement.setServiceId(req.getServiceId());
        signalement.setStatut(StatutSignalement.EN_ATTENTE);
        signalement.setPriorite(req.getPriorite());
        // Nouvelle gestion de la géolocalisation
        signalement.setLatitude(req.getLatitude());
        signalement.setLongitude(req.getLongitude());

        if (req.getFichiersPaths() != null) {
            signalement.setFichiersPaths(req.getFichiersPaths());
        }else {
            signalement.setFichiersPaths(null);
        }
        if (req.getTraiteurUuid() != null) {
            signalement.setUtilisateurTraiteur(
                autoriteRepository.findByTrackingId(req.getTraiteurUuid()).orElse(null)
            );
        } else {
            signalement.setUtilisateurTraiteur(null);
        }
        if (req.getOuvrierUuid() != null) {
            signalement.setOuvrier(
                ouvrierRepository.findByTrackingId(req.getOuvrierUuid()).orElse(null)
            );
        } else {
            signalement.setOuvrier(null);
        }

        List<UUID> ids = req.getFichiers();
            if (ids != null && !ids.isEmpty()) {
                List<FichierJoin> fichiers = ids.stream()
                    .map(id -> fichierJoinRepository.findByTrackingId(id)
                        .orElseThrow(() -> new RuntimeException("Aucun fichier trouvé pour l'ID : " + id)))
                    .collect(Collectors.toList());
                signalement.setFichiers(fichiers);
            } else {
                signalement.setFichiers(Collections.emptyList()); 
            }

        return signalement;
    }

    public SignalementResponse toResponse(Signalement signalement) {
        SignalementResponse response = new SignalementResponse();
        response.setTrackingId(signalement.getTrackingId());
        response.setTitre(signalement.getTitre());
        response.setCode(signalement.getCode());
        response.setDescription(signalement.getDescription());
        response.setStatut(signalement.getStatut());
        response.setTypeService(signalement.getTypeService());
        response.setServiceId(signalement.getServiceId());
        response.setCommentaireService(signalement.getCommentaireService());
        response.setPriorite(signalement.getPriorite());
        response.setLatitude(signalement.getLatitude());
        response.setLongitude(signalement.getLongitude());

        if (signalement.getFichiersPaths() != null) {
            response.setFichiersPaths(signalement.getFichiersPaths());
        }else {
            response.setFichiersPaths(null);
        }

        if (signalement.getOuvrier() != null) {
            response.setOuvrierUuid(signalement.getOuvrier().getTrackingId());
        }else { signalement.setOuvrier(null);}

        if (signalement.getUtilisateurTraiteur() != null) {
            response.setTraiteurUuid(signalement.getUtilisateurTraiteur().getTrackingId());
        }else {
            signalement.setUtilisateurTraiteur(null);}

        List<FichierJoin> obj = signalement.getFichiers();
        List<UUID> fichiersUUID = obj.stream()
                .map(FichierJoin::getTrackingId)
                .collect(Collectors.toList());
        response.setFichiers(fichiersUUID);

        return response;
    }
    
}
