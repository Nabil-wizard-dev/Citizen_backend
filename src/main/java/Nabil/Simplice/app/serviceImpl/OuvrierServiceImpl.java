package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.entity.Ouvrier;
import Nabil.Simplice.app.repository.OuvrierRepository;
import Nabil.Simplice.app.repository.SignalementRepository;
import Nabil.Simplice.app.utils.ApiResponse;
import Nabil.Simplice.app.service.OuvrierService;
import Nabil.Simplice.app.mappers.OuvrierMappers;
import Nabil.Simplice.app.dto.request.OuvrierRequest;
import Nabil.Simplice.app.dto.response.OuvrierResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OuvrierServiceImpl implements OuvrierService {
    
    private final OuvrierRepository repository;
    private final OuvrierMappers mappers;
    private final SignalementRepository signalementRepository;

    public OuvrierServiceImpl(OuvrierRepository repository,OuvrierMappers mappers, SignalementRepository signalementRepository) {
        this.repository = repository;
        this.mappers = mappers;
        this.signalementRepository = signalementRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<OuvrierResponse>> creerOuvrier(OuvrierRequest request) {
        try {
            Ouvrier saved = repository.save(mappers.toEntity(request));
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la création", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<OuvrierResponse>>> getAll() {
        try {
            List<OuvrierResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OuvrierResponse>> getById(UUID id) {
        try {
            OuvrierResponse response = repository.findByTrackingId(id)
                    .map(mappers::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Ouvrier non trouvé avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OuvrierResponse>> updateOuvrier(UUID trackingId, OuvrierRequest req) {
        try {
            if (!repository.existsByTrackingId(trackingId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Ouvrier introuvable", null));
            } else {

                Ouvrier existingOuvrier = repository.findByTrackingId(trackingId).get();

                if (req.getCni() != null) {
                    existingOuvrier.setCni(req.getCni());
                }

                if (req.getEmail() != null) {
                    existingOuvrier.setEmail(req.getEmail());
                }

                if (req.getMotDePasse() != null && !req.getMotDePasse().isEmpty()) {
                    existingOuvrier.setMotDePasse(mappers.getPasswordEncoder().encode(req.getMotDePasse()));
                }

                if (req.getNom() != null) {
                    existingOuvrier.setNom(req.getNom());
                }

                if (req.getPrenom() != null) {
                    existingOuvrier.setPrenom(req.getPrenom());
                }

                if (req.getNumero() != 0) {
                    existingOuvrier.setNumero(req.getNumero());
                }

                if (req.getDateNaissance() != null) {
                    existingOuvrier.setDateNaissance(req.getDateNaissance());
                }

                if (req.getAdresse() != null) {
                    existingOuvrier.setAdresse(req.getAdresse());
                }

                if (req.getRole() != null) {
                    existingOuvrier.setRole(req.getRole());
                }

                if (req.getSignalementActuelId() != null) {
                    existingOuvrier.setSignalementActuelId(req.getSignalementActuelId());
                }

                if (req.getSpecialite() != null) {
                    existingOuvrier.setSpecialite(req.getSpecialite());
                }

                if (req.getServiceId() != null) {
                    existingOuvrier.setServiceId(req.getServiceId());
                }

                Ouvrier updatedOuvrier = repository.save(existingOuvrier);
                OuvrierResponse response = mappers.toResponse(updatedOuvrier);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise à jour de l'ouvrier.", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<OuvrierResponse>> updateOuvrierSignalementActuId(UUID id, UUID signalementActuelId) {
        try {
            if (!repository.existsByTrackingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Ouvrier introuvable", null));
            } else {
                if (signalementActuelId != null && !signalementRepository.existsByTrackingId(signalementActuelId)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Signalement introuvable", null));
                }else{
                    Ouvrier existingOuvrier = repository.findByTrackingId(id).get();
                existingOuvrier.setSignalementActuelId(signalementActuelId);

                OuvrierResponse response = mappers.toResponse(repository.save(existingOuvrier));

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
                }
                
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise à jour de l'ouvrier.", null));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(UUID id) {
        try {
            if (!repository.existsByTrackingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "Ouvrier non trouvé avec l'ID : " + id, null));
            }
            else {
                repository.delete(repository.findByTrackingId(id).orElseThrow());
                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la suppression", null));
        }
    }
}
