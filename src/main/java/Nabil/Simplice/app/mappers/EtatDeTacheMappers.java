package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.repository.*;
import org.springframework.stereotype.Component;
import java.util.UUID;

import Nabil.Simplice.app.entity.EtatDeTache;
import Nabil.Simplice.app.dto.request.EtatDeTacheRequest;
import Nabil.Simplice.app.dto.response.EtatDeTacheResponse;

@Component
public class EtatDeTacheMappers {
    private final TacheRepository tacheRepository;
    public EtatDeTacheMappers(TacheRepository tacheRepository) {
        this.tacheRepository = tacheRepository;
    }

    public EtatDeTache toEntity(EtatDeTacheRequest req) {
        EtatDeTache etatDeTache = new EtatDeTache();
        etatDeTache.setTrackingId(UUID.randomUUID());
        etatDeTache.setDescription(req.getDescription());

        if (req.getFichiersPaths() != null) {
            etatDeTache.setFichiersPaths(req.getFichiersPaths());
        }

        if (req.getTache() != null) {
            etatDeTache.setTache(
                tacheRepository.findByTrakingId(req.getTache()).orElseThrow()
            );
        } else {
            etatDeTache.setTache(null);
        }

        // Les fichiers sont maintenant gérés directement par le service

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

        // La gestion des anciens fichiers par UUID est supprimée

        return response;
    }
}
