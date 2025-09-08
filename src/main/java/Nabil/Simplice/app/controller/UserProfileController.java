package Nabil.Simplice.app.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Nabil.Simplice.app.dto.request.ProfileUpdateRequest;
import Nabil.Simplice.app.dto.response.ProfileResponse;
import Nabil.Simplice.app.service.ProfileService;
import Nabil.Simplice.app.utils.ApiResponse;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final ProfileService profileService;

    public UserProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ProfileResponse>> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        // Pour l'instant, on utilise l'email pour récupérer le trackingId
        // Dans une implémentation complète, on pourrait stocker le trackingId dans le token JWT
        // ou créer un endpoint spécifique pour récupérer le profil par email
        return ResponseEntity.ok(ApiResponse.error("Endpoint à implémenter avec récupération du trackingId"));
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable UUID trackingId) {
        return profileService.getProfile(trackingId);
    }

    @PutMapping("/{trackingId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @PathVariable UUID trackingId,
            @RequestBody ProfileUpdateRequest request) {
        return profileService.updateProfile(trackingId, request);
    }

    @PostMapping(value = "/{trackingId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadProfilePhoto(
            @PathVariable UUID trackingId,
            @RequestPart("photo") MultipartFile photo) {
        return profileService.uploadProfilePhoto(trackingId, photo);
    }

    @DeleteMapping("/{trackingId}/photo")
    public ResponseEntity<ApiResponse<String>> deleteProfilePhoto(@PathVariable UUID trackingId) {
        return profileService.deleteProfilePhoto(trackingId);
    }
} 