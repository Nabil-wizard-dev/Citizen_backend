package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.AnnonceRequest;
import Nabil.Simplice.app.dto.response.AnnonceResponse;
import Nabil.Simplice.app.entity.Annonce;
import Nabil.Simplice.app.mappers.AnnonceMappers;
import Nabil.Simplice.app.repository.AnnonceRepository;
import Nabil.Simplice.app.service.AnnonceService;
import Nabil.Simplice.app.service.FichierJoinService;
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
import java.util.stream.Collectors;

@Service
public class AnnonceServiceImpl implements AnnonceService {

    private AnnonceRepository repository;
    private AnnonceMappers mappers;
    private FichierJoinService fichierJoinService;

    public AnnonceServiceImpl(AnnonceRepository repository, AnnonceMappers mappers, FichierJoinService fichierJoinService) {
        this.repository = repository;
        this.mappers = mappers;
        this.fichierJoinService = fichierJoinService;
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

            List<String> fichiersPaths = new ArrayList<>();// pour stocker les chemins

            //mettre en place les fichiers join
            for (MultipartFile fichier : fichiers) {
                ResponseEntity<ApiResponse<String>> jsonResult = fichierJoinService.uploadFichier(fichier);

                if (jsonResult.getBody().getData() != null) {
                    fichiersPaths.add(jsonResult.getBody().getData());
                }else{
                    fichiersPaths = null;
                }
            }
            System.out.println(fichiersPaths);

            // creer le signalement
            a.setFichierPaths(fichiersPaths); //mettre a jr le champ
            Annonce saved = repository.save(mappers.toEntity(a));

            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
