package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.ProfileUpdateRequest;
import Nabil.Simplice.app.dto.response.ProfileResponse;
import Nabil.Simplice.app.entity.Utilisateur;
import Nabil.Simplice.app.repository.UtilisateurRepository;
import Nabil.Simplice.app.service.FichierJoinService;
import Nabil.Simplice.app.service.ProfileService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final UtilisateurRepository utilisateurRepository;
    private final FichierJoinService fichierJoinService;

    public ProfileServiceImpl(UtilisateurRepository utilisateurRepository, FichierJoinService fichierJoinService) {
        this.utilisateurRepository = utilisateurRepository;
        this.fichierJoinService = fichierJoinService;
    }

    @Override
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(UUID trackingId) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByTrackingId(trackingId)
                    .orElse(null);

            if (utilisateur == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Utilisateur non trouvé"));
            }

            ProfileResponse response = new ProfileResponse(
                    utilisateur.getTrackingId(),
                    utilisateur.getNom(),
                    utilisateur.getPrenom(),
                    utilisateur.getEmail(),
                    utilisateur.getNumero(),
                    utilisateur.getAdresse(),
                    utilisateur.getDateNaissance(),
                    utilisateur.getCni(),
                    utilisateur.getRole(),
                    utilisateur.getPhotoProfil()
            );

            return ResponseEntity.ok(ApiResponse.success(response, "Profil récupéré avec succès"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération du profil: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(UUID trackingId, ProfileUpdateRequest request) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByTrackingId(trackingId)
                    .orElse(null);

            if (utilisateur == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Utilisateur non trouvé"));
            }

            // Mettre à jour les champs
            if (request.getNom() != null) {
                utilisateur.setNom(request.getNom());
            }
            if (request.getPrenom() != null) {
                utilisateur.setPrenom(request.getPrenom());
            }
            if (request.getEmail() != null) {
                // Vérifier si l'email n'est pas déjà utilisé par un autre utilisateur
                if (!request.getEmail().equals(utilisateur.getEmail())) {
                    if (utilisateurRepository.existsByEmail(request.getEmail())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error("Cet email est déjà utilisé"));
                    }
                }
                utilisateur.setEmail(request.getEmail());
            }
            if (request.getNumero() != 0) {
                // Vérifier si le numéro n'est pas déjà utilisé par un autre utilisateur
                if (request.getNumero() != utilisateur.getNumero()) {
                    if (utilisateurRepository.existsByNumero(request.getNumero())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error("Ce numéro est déjà utilisé"));
                    }
                }
                utilisateur.setNumero(request.getNumero());
            }
            if (request.getAdresse() != null) {
                utilisateur.setAdresse(request.getAdresse());
            }
            if (request.getDateNaissance() != null) {
                utilisateur.setDateNaissance(request.getDateNaissance());
            }
            if (request.getPhotoProfil() != null) {
                utilisateur.setPhotoProfil(request.getPhotoProfil());
            }

            Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);

            ProfileResponse response = new ProfileResponse(
                    updatedUtilisateur.getTrackingId(),
                    updatedUtilisateur.getNom(),
                    updatedUtilisateur.getPrenom(),
                    updatedUtilisateur.getEmail(),
                    updatedUtilisateur.getNumero(),
                    updatedUtilisateur.getAdresse(),
                    updatedUtilisateur.getDateNaissance(),
                    updatedUtilisateur.getCni(),
                    updatedUtilisateur.getRole(),
                    updatedUtilisateur.getPhotoProfil()
            );

            return ResponseEntity.ok(ApiResponse.success(response, "Profil mis à jour avec succès"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la mise à jour du profil: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> uploadProfilePhoto(UUID trackingId, MultipartFile photo) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByTrackingId(trackingId)
                    .orElse(null);

            if (utilisateur == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Utilisateur non trouvé"));
            }

            // Supprimer l'ancienne photo si elle existe
            if (utilisateur.getPhotoProfil() != null && !utilisateur.getPhotoProfil().isEmpty()) {
                deleteOldPhoto(utilisateur.getPhotoProfil());
            }

            // Uploader la nouvelle photo
            ResponseEntity<ApiResponse<String>> uploadResult = fichierJoinService.uploadFichier(photo);
            
            if (uploadResult.getBody() != null && uploadResult.getBody().getData() != null) {
                String photoPath = uploadResult.getBody().getData();
                utilisateur.setPhotoProfil(photoPath);
                utilisateurRepository.save(utilisateur);

                return ResponseEntity.ok(ApiResponse.success(photoPath, "Photo de profil uploadée avec succès"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.error("Erreur lors de l'upload de la photo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de l'upload de la photo: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteProfilePhoto(UUID trackingId) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findByTrackingId(trackingId)
                    .orElse(null);

            if (utilisateur == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Utilisateur non trouvé"));
            }

            if (utilisateur.getPhotoProfil() != null && !utilisateur.getPhotoProfil().isEmpty()) {
                deleteOldPhoto(utilisateur.getPhotoProfil());
                utilisateur.setPhotoProfil(null);
                utilisateurRepository.save(utilisateur);
            }

            return ResponseEntity.ok(ApiResponse.success("Photo de profil supprimée avec succès"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la suppression de la photo: " + e.getMessage()));
        }
    }

    private void deleteOldPhoto(String photoPath) {
        try {
            if (photoPath != null && !photoPath.isEmpty()) {
                File photoFile = new File(photoPath);
                if (photoFile.exists()) {
                    photoFile.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 