package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.entity.EtatDeTache;
import Nabil.Simplice.app.repository.EtatDeTacheRepository;
import Nabil.Simplice.app.service.FileStorageService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Nabil.Simplice.app.service.EtatDeTacheService;
import Nabil.Simplice.app.mappers.EtatDeTacheMappers;
import Nabil.Simplice.app.dto.request.EtatDeTacheRequest;
import Nabil.Simplice.app.dto.response.EtatDeTacheResponse;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class EtatDeTacheServiceImpl implements EtatDeTacheService {
    
    private final EtatDeTacheRepository repository;
    private final EtatDeTacheMappers mappers;
    private final FileStorageService fileStorageService;

    public EtatDeTacheServiceImpl(EtatDeTacheRepository repository, EtatDeTacheMappers mappers, FileStorageService fileStorageService) {
        this.repository = repository;
        this.mappers = mappers;
        this.fileStorageService = fileStorageService;
    }



    @Override
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTache(EtatDeTacheRequest request) {
        try {
            EtatDeTache saved = repository.save(mappers.toEntity(request));
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la création", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTacheAvecFichiers(EtatDeTacheRequest request, List<MultipartFile> fichiers) {
        try {
            // Créer l'état de tâche d'abord
            EtatDeTache etatDeTache = mappers.toEntity(request);
            EtatDeTache saved = repository.save(etatDeTache);

            // Gérer les fichiers s'il y en a
            if (fichiers != null && !fichiers.isEmpty()) {
                List<String> filePaths = fileStorageService.storeFiles(fichiers);
                saved.setFichiersPaths(filePaths);
                saved = repository.save(saved);
            }

            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "État de tâche créé avec succès", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la création: " + e.getMessage(), null));
        }
    }

@Override
public ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTacheComplet(EtatDeTacheRequest request, MultipartFile fichier) {
    try {
        String fichierPath = null;

        if (fichier != null && !fichier.isEmpty()) {
            // Téléverser le fichier via le service de stockage
            List<String> paths = fileStorageService.storeFiles(java.util.List.of(fichier));
            if (paths != null && !paths.isEmpty()) {
                fichierPath = paths.get(0);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(LocalDateTime.now(), false, "Échec de l'upload du fichier", null));
            }
        }

        // Mettre à jour le chemin du fichier dans la requête
        request.setFichiersPaths(fichierPath != null ? java.util.List.of(fichierPath) : null);

        // Sauvegarder l'entité
        EtatDeTache saved = repository.save(mappers.toEntity(request));

        return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Succès", mappers.toResponse(saved)));

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la création de l'état de tâche", null));
    }
}



    @Override
    public ResponseEntity<ApiResponse<List<EtatDeTacheResponse>>> getAll() {
        try {
            List<EtatDeTacheResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> getById(UUID id) {
        try {
            EtatDeTacheResponse response = repository.findByTrackingId(id)
                    .map(mappers::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("État de tâche non trouvé avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<EtatDeTacheResponse>>> findByTacheId(UUID TacheId) {
        try {
            List<EtatDeTacheResponse> response = repository.getAllByTacheId(TacheId)
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la récupération", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> updateEtatDeTache(UUID trackingId, EtatDeTacheRequest req) {
        try {
            if (!repository.existsByTrackingId(trackingId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "État de tâche introuvable", null));
            } else {

                EtatDeTache existingEtatDeTache = repository.findByTrackingId(trackingId).get();

                if (req.getDescription() != null) {
                    existingEtatDeTache.setDescription(req.getDescription());
                }

                EtatDeTache updatedEtatDeTache = repository.save(existingEtatDeTache);
                EtatDeTacheResponse response = mappers.toResponse(updatedEtatDeTache);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise à jour de l'état de tâche.", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(UUID id) {
        try {
            if (!repository.existsByTrackingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "État de tâche non trouvé avec l'ID : " + id, null));
            } else {
                repository.delete(repository.findByTrackingId(id).orElseThrow());
                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la suppression", null));
        }
    }
}
