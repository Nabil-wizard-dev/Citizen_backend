package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.ProfileUpdateRequest;
import Nabil.Simplice.app.dto.response.ProfileResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileService {
    
    // Récupérer le profil de l'utilisateur connecté
    ResponseEntity<ApiResponse<ProfileResponse>> getProfile(UUID trackingId);
    
    // Mettre à jour le profil de l'utilisateur
    ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(UUID trackingId, ProfileUpdateRequest request);
    
    // Uploader une photo de profil
    ResponseEntity<ApiResponse<String>> uploadProfilePhoto(UUID trackingId, MultipartFile photo);
    
    // Supprimer la photo de profil
    ResponseEntity<ApiResponse<String>> deleteProfilePhoto(UUID trackingId);
} 