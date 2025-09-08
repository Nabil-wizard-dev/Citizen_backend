package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.entity.EtatDeTache;
import Nabil.Simplice.app.repository.EtatDeTacheRepository;
import Nabil.Simplice.app.service.FichierJoinService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final FichierJoinService fichierJoinService;

    public EtatDeTacheServiceImpl(EtatDeTacheRepository repository, EtatDeTacheMappers mappers, FichierJoinService fichierJoinService) {
        this.repository = repository;
        this.mappers = mappers;
        this.fichierJoinService = fichierJoinService;
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

    // nouvelle methode de creation
//    @Override
//    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTacheComplet(EtatDeTacheRequest request, MultipartFile fichiers) {
//        try {
//
//            List<String> fichiersPaths = new ArrayList<>();// pour stocker les chemins
//
//            //mettre en place les fichiers join
//            for (MultipartFile fichier : fichiers) {
//                ResponseEntity<ApiResponse<String>> jsonResult = fichierJoinService.uploadFichier(fichier);
//
//                if (jsonResult.getBody().getData() != null) {
//                    fichiersPaths.add(jsonResult.getBody().getData());
//                }else{
//                    fichiersPaths = null;
//                }
//            }
//            System.out.println(fichiersPaths);
//
//            // creer le signalement
////            UUID tacheUUID = UUID.fromString(request.getTache());
//            request.setFichiersPaths(fichiersPaths); //mettre a jr le champ
//            EtatDeTache saved = repository.save(mappers.toEntity(request));
//
//            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
//        }
//    }

@Override
public ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTacheComplet(EtatDeTacheRequest request, MultipartFile fichier) {
    try {
        String fichierPath = null;

        if (fichier != null && !fichier.isEmpty()) {
            // Téléverser le fichier
            ResponseEntity<ApiResponse<String>> jsonResult = fichierJoinService.uploadFichier(fichier);

            if (jsonResult.getBody() != null && jsonResult.getBody().getData() != null) {
                fichierPath = jsonResult.getBody().getData(); // chemin du fichier
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(LocalDateTime.now(), false, "Échec de l'upload du fichier", null));
            }
        }

        // Mettre à jour le chemin du fichier dans la requête
        request.setFichiersPaths(fichierPath);

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
