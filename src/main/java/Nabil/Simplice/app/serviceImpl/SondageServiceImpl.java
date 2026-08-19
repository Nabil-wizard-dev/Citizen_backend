package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.entity.Sondage;
import Nabil.Simplice.app.mappers.SondageMappers;
import Nabil.Simplice.app.repository.SignalementRepository;
import Nabil.Simplice.app.repository.SondageRepository;
import Nabil.Simplice.app.service.FileStorageService;
import Nabil.Simplice.app.service.SondageService;
import Nabil.Simplice.app.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SondageServiceImpl implements SondageService {

    private SondageRepository repository;
    private SondageMappers mappers;
    private SignalementRepository signalementRepository;
    private FileStorageService fileStorageService;

    public SondageServiceImpl(SondageRepository repository, SondageMappers mappers, SignalementRepository signalementRepository, FileStorageService fileStorageService) {
        this.repository = repository;
        this.mappers = mappers;
        this.signalementRepository = signalementRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondages() {
        try{
            List<SondageResponse> all = repository.findAll().stream().map(mappers::toResponse).toList();
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondagesByEntityId(UUID entityId) {
        try {
            List<SondageResponse> all = repository.findAllByEntiteId(entityId).stream().map(mappers::toResponse).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> getSondageById(UUID id) {
        try {
            SondageResponse response = repository.findByTrackingId(id).map(mappers::toResponse).orElseThrow(() -> new EntityNotFoundException("Annonce non trouvé avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", response));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> addSondage(SondageRequest a) {
        try {
            // verification du signalement auquel le sondage est relie
            if (a.getEntiteId() == null || !signalementRepository.existsByTrackingId(a.getEntiteId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "Signalement non trouvé avec l'ID : " + a.getEntiteId(), null));
            }

            Sondage saved = repository.save(mappers.toEntity(a));

            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la creation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> updateSondage(UUID id, SondageRequest a) {
        try {
            if (!repository.existsSondageByTrackingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "Sondage introuvable", null));
            } else {
                // recuperation du sondage a travers le trackingId
                Sondage existingSondage = repository.findByTrackingId(id).get();

                if (a.getEntiteId() != null) {
                    existingSondage.setEntiteId(a.getEntiteId());
                }

                if (a.getCommentaire() != null) {
                    existingSondage.setCommentaire(a.getCommentaire());
                }

                if (a.getNote() != 0) {
                    existingSondage.setNote(a.getNote());
                }

                existingSondage.setConfirmation(a.isConfirmation());

                Sondage updatedSondage = repository.save(existingSondage);
                SondageResponse response = mappers.toResponse(updatedSondage);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise a jr", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> deleteSondage(UUID id) {
        try {
            if (!repository.existsSondageByTrackingId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "\"Signalement non trouvé avec l'ID : \" + id", null));
            }else {
                repository.delete(repository.findByTrackingId(id).orElseThrow());
                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }
}
