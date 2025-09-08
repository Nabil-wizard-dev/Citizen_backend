package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.repository.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Nabil.Simplice.app.entity.Tache;
import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.entity.EtatDeTache;
import Nabil.Simplice.app.dto.request.TacheRequest;
import Nabil.Simplice.app.dto.response.TacheResponse;

@Component
public class TacheMappers {
    private final SignalementRepository signalementRepository;
    private final EtatDeTacheRepository etatDeTacheRepository;

    public TacheMappers(
            SignalementRepository signalementRepository,
            EtatDeTacheRepository etatDeTacheRepository) {
        this.signalementRepository = signalementRepository;
        this.etatDeTacheRepository = etatDeTacheRepository;
    }

    public Tache toEntity(TacheRequest req) throws IOException {
        Tache tache = new Tache();
        tache.setTrakingId(UUID.randomUUID());
        tache.setDateDebut(req.getDateDebut());
        tache.setDateFin(req.getDateFin());
        tache.setActiver(req.isActiver());
        tache.setResolu(req.isResolu());
        tache.setSignalement(signalementRepository.findByTrackingId(req.getSignalementId()).orElseThrow(() -> new RuntimeException("Signalement introuvable")));
        System.out.println(tache.getSignalement().getTrackingId());

//        if (req.getFichierDevis() != null) {
//        tache.setFichierDevis(req.getFichierDevis().getBytes());
//        }else {
//            tache.setFichierDevis(null);
//        }
//        if (req.getSignalementId() != null  ) {
//            tache.setSignalement(signalementRepository.findByTrackingId(req.getSignalementId()).orElseThrow(() -> new RuntimeException("Signalement introuvable"))
//            );
//        } else {
//            tache.setSignalement(null);
//        }

        if (req.getEtatDeTacheIds() != null && !req.getEtatDeTacheIds().isEmpty()) {
            List<UUID> ids = req.getEtatDeTacheIds();
            List<EtatDeTache> etatsDeTache = ids.stream()
                    .map(id -> etatDeTacheRepository.findByTrackingId(id)
                            .orElseThrow(() -> new RuntimeException("Aucun état de tâche trouvé pour l'ID : " + id)))
                    .collect(Collectors.toList());
            tache.setEtatsDeTache(etatsDeTache);
        }

        return tache;
    }

    public TacheResponse toResponse(Tache tache) {
        TacheResponse response = new TacheResponse();
        response.setTrackingId(tache.getTrakingId());
        response.setDateDebut(tache.getDateDebut());
        response.setDateFin(tache.getDateFin());
        response.setActiver(tache.isActiver());
        response.setResolu(tache.isResolu());
        response.setSignalementId(tache.getSignalement().getTrackingId());

        if (tache.getFichierDevis() != null ) {
            response.setFichierDevis(Base64.getEncoder().encodeToString(tache.getFichierDevis()));
        }else {
            response.setFichierDevis(null);
        }

//        if (tache.getSignalement() != null) {
//            response.setSignalementId(tache.getSignalement().getTrackingId());
//        } else {
//            response.setSignalementId(null);
//        }

        if (tache.getEtatsDeTache() != null && !tache.getEtatsDeTache().isEmpty()) {
            List<EtatDeTache> etats = tache.getEtatsDeTache();
            List<UUID> etatsUUID = etats.stream()
                    .map(EtatDeTache::getTrackingId)
                    .collect(Collectors.toList());
            response.setEtatDeTacheIds(etatsUUID);
        }

        return response;
    }
}
