package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.AnnonceRequest;
import Nabil.Simplice.app.dto.response.AnnonceResponse;
import Nabil.Simplice.app.entity.Annonce;
import Nabil.Simplice.app.mappers.AnnonceMappers;
import Nabil.Simplice.app.repository.AnnonceRepository;
import Nabil.Simplice.app.service.AnnonceService;
import Nabil.Simplice.app.service.FileStorageService;
import Nabil.Simplice.app.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class AnnonceServiceImpl implements AnnonceService {

    private static final Logger logger = LoggerFactory.getLogger(AnnonceServiceImpl.class);
    
    private AnnonceRepository repository;
    private AnnonceMappers mappers;
    private FileStorageService fileStorageService;

    public AnnonceServiceImpl(AnnonceRepository repository, AnnonceMappers mappers, FileStorageService fileStorageService) {
        this.repository = repository;
        this.mappers = mappers;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ResponseEntity<ApiResponse<List<AnnonceResponse>>> getAllAnnonces() {
        try {
            List<AnnonceResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<AnnonceResponse>>> getAllAnnoncesByEntityId(UUID entityId) {
        try {
            List<AnnonceResponse> all = repository.findAllByEntiteId(entityId)
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<AnnonceResponse>> getAnnonceById(UUID id) {
        try {
            AnnonceResponse Response = repository.findByTrackingId(id)
                    .map(mappers::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Annonce non trouvé avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", Response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<AnnonceResponse>> addAnnonce(AnnonceRequest a, List<MultipartFile> fichiers) {
        try {

            // Stocker les fichiers
            List<String> fichiersPaths = fileStorageService.storeFiles(fichiers);
            System.out.println(fichiersPaths);

            // creer le signalement
            a.setFichierPaths(fichiersPaths); //mettre a jr le champ
            Annonce saved = repository.save(mappers.toEntity(a));

            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            logger.error("Erreur lors de la création de l'annonce", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la creation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<AnnonceResponse>> updateAnnonce(UUID id, AnnonceRequest a) {
        try {

            if (!repository.existsByTrackingId(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Annonce introuvable", null));
            }else {
                // recuperation du trackingId
                Annonce existingAnnonce = repository.findByTrackingId(id).get();

                if (a.getEntiteId() != null) {
                    existingAnnonce.setEntiteId(a.getEntiteId());
                }

                if (a.getMessage() != null) {
                    existingAnnonce.setMessage(a.getMessage());
                }

                if (a.getFichierPaths() != null && !a.getFichierPaths().isEmpty()) {
                    existingAnnonce.setFichiersPaths(a.getFichierPaths());
                }

                Annonce updatedAnnonce = repository.save(existingAnnonce);
                AnnonceResponse response = mappers.toResponse(updatedAnnonce);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }

        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'annonce", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise a jr", null));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<Void>> deleteAnnonce(UUID id) {
        try {
            if (!repository.existsByTrackingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "\"Signalement non trouvé avec l'ID : \" + id", null));
            }
            else {
                repository.delete(repository.findByTrackingId(id).orElseThrow());
                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", null));
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }
}
