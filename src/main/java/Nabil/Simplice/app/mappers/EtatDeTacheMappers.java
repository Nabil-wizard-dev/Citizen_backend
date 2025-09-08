package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.repository.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Nabil.Simplice.app.entity.EtatDeTache;
import Nabil.Simplice.app.entity.FichierJoin;
import Nabil.Simplice.app.dto.request.EtatDeTacheRequest;
import Nabil.Simplice.app.dto.response.EtatDeTacheResponse;

@Component
public class EtatDeTacheMappers {
    private final TacheRepository tacheRepository;
    private final FichierJoinRepository fichierJoinRepository;

    public EtatDeTacheMappers(
            TacheRepository tacheRepository,
            FichierJoinRepository fichierJoinRepository) {
        this.tacheRepository = tacheRepository;
        this.fichierJoinRepository = fichierJoinRepository;
    }

    public EtatDeTache toEntity(EtatDeTacheRequest req) {
        EtatDeTache etatDeTache = new EtatDeTache();
        etatDeTache.setTrackingId(UUID.randomUUID());
        etatDeTache.setDescription(req.getDescription());

        if (req.getFichiersPaths() != null) {
            etatDeTache.setFichiersPaths(req.getFichiersPaths());
        }else {
            etatDeTache.setFichiersPaths(null);
        }

        if (req.getTache() != null) {
            etatDeTache.setTache(
                tacheRepository.findByTrakingId(req.getTache()).orElseThrow()
            );
        } else {
            etatDeTache.setTache(null);
        }

        if (req.getFichiers() != null && !req.getFichiers().isEmpty()) {
            List<UUID> ids = req.getFichiers();
            List<FichierJoin> fichiers = ids.stream()
                    .map(id -> fichierJoinRepository.findByTrackingId(id)
                            .orElseThrow(() -> new RuntimeException("Aucun fichier trouvé pour l'ID : " + id)))
                    .collect(Collectors.toList());
            etatDeTache.setFichiers(fichiers);
        }

        return etatDeTache;
    }

    public EtatDeTacheResponse toResponse(EtatDeTache etatDeTache) {
        EtatDeTacheResponse response = new EtatDeTacheResponse();
        response.setTrackingId(etatDeTache.getTrackingId());
        response.setDescription(etatDeTache.getDescription());

        if (etatDeTache.getTache() != null) {
            response.setTache(etatDeTache.getTache().getTrakingId());
        } else {
            response.setTache(null);
        }

        if (etatDeTache.getFichiersPaths() != null) {
            response.setFichiersPaths(etatDeTache.getFichiersPaths());
        }else {
            response.setFichiersPaths(null);
        }

        if (etatDeTache.getFichiers() != null && !etatDeTache.getFichiers().isEmpty()) {
            List<FichierJoin> fichiers = etatDeTache.getFichiers();
            List<UUID> fichiersUUID = fichiers.stream()
                    .map(FichierJoin::getTrackingId)
                    .collect(Collectors.toList());
            response.setFichiers(fichiersUUID);
        }

        return response;
    }
}
