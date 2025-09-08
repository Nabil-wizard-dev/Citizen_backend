package Nabil.Simplice.app.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import Nabil.Simplice.app.dto.request.TacheRequest;
import Nabil.Simplice.app.dto.response.TacheResponse;
import Nabil.Simplice.app.entity.Tache;
import Nabil.Simplice.app.mappers.TacheMappers;
import Nabil.Simplice.app.repository.SignalementRepository;
import Nabil.Simplice.app.repository.TacheRepository;
import Nabil.Simplice.app.service.TacheService;
import Nabil.Simplice.app.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class TacheServiceImpl implements TacheService {
    
    private final TacheRepository repository;
    private final TacheMappers mappers;
    private final SignalementRepository signalementRepository;

    public TacheServiceImpl(TacheRepository repository,
                            TacheMappers mappers,
                            SignalementRepository signalementRepository) {
        this.repository = repository;
        this.mappers = mappers;
        this.signalementRepository = signalementRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<TacheResponse>> creerTache(TacheRequest request) {
        try {
            System.out.println(request.getSignalementId());
            System.out.println(request.isActiver());
            System.out.println(request.isResolu());
            Tache saved = repository.save(mappers.toEntity(request));
            System.out.println("lolgsgsagsdgsdagdsgsgsagdsfgsdfgsdgsd  "+ saved.getSignalement());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la création", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<TacheResponse>>> getAll() {
        try {
            List<TacheResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<TacheResponse>> getById(UUID id) {
        try {
            TacheResponse response = repository.findByTrakingId(id)
                    .map(mappers::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<TacheResponse>> findBySignalementId(UUID id) {
        try {
            TacheResponse response = repository.findBySignalementId(id)
                    .map(mappers::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<TacheResponse>> updateTache(UUID trackingId, TacheRequest req, MultipartFile fichierDevis) {
        try {
            if (!repository.existsByTrakingId(trackingId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Tâche introuvable", null));
            } else {

                Tache existingTache = repository.findByTrakingId(trackingId).get();

                if (req.getDateDebut() != null) {
                    existingTache.setDateDebut(req.getDateDebut());
                }

                if (req.getDateFin() != null) {
                    existingTache.setDateFin(req.getDateFin());
                }
                if (fichierDevis != null) {
                    existingTache.setFichierDevis(fichierDevis.getBytes());
                }

                existingTache.setActiver(req.isActiver());
                existingTache.setResolu(req.isResolu());

                Tache updatedTache = repository.save(existingTache);
                TacheResponse response = mappers.toResponse(updatedTache);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise à jour de la tâche.", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<TacheResponse>> updateTacheResolu(UUID id) {
        try {
            if (!repository.existsByTrakingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Tâche introuvable", null));
            } else {

                Tache existingTache = repository.findByTrakingId(id).get();


                existingTache.setResolu(true);
                Tache updatedTache = repository.save(existingTache);
                TacheResponse response = mappers.toResponse(updatedTache);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise à jour de la tâche.", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<TacheResponse>> updateTacheActiver(UUID id) {
        try {
            if (!repository.existsByTrakingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Tâche introuvable", null));
            } else {

                Tache existingTache = repository.findByTrakingId(id).get();


                existingTache.setActiver(true);
                Tache updatedTache = repository.save(existingTache);
                TacheResponse response = mappers.toResponse(updatedTache);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise à jour de la tâche.", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(UUID id) {
        try {
            if (!repository.existsByTrakingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "Tâche non trouvée avec l'ID : " + id, null));
            } else {
                repository.delete(repository.findByTrakingId(id).orElseThrow());
                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la suppression", null));
        }
    }
}
