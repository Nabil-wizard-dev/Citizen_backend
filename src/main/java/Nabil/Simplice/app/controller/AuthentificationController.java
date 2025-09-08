package Nabil.Simplice.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import Nabil.Simplice.app.dto.request.LoginRequest;
import Nabil.Simplice.app.dto.request.RegisterRequest;
import Nabil.Simplice.app.dto.response.LoginResponse;
import Nabil.Simplice.app.dto.response.RegisterResponse;
import Nabil.Simplice.app.service.AuthentificationService;
import Nabil.Simplice.app.entity.Utilisateur;
import Nabil.Simplice.app.entity.Ouvrier;
import Nabil.Simplice.app.repository.UtilisateurRepository;
import Nabil.Simplice.app.repository.OuvrierRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {

    private final AuthentificationService authentificationService;
    private final UtilisateurRepository utilisateurRepository;
    private final OuvrierRepository ouvrierRepository;

    public AuthentificationController(
        AuthentificationService authentificationService,
        UtilisateurRepository utilisateurRepository,
        OuvrierRepository ouvrierRepository
    ) {
        this.authentificationService = authentificationService;
        this.utilisateurRepository = utilisateurRepository;
        this.ouvrierRepository = ouvrierRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authentificationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authentificationService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("trackingId", user.getTrackingId());
            userData.put("nom", user.getNom());
            userData.put("prenom", user.getPrenom());
            userData.put("email", user.getEmail());
            userData.put("cni", user.getCni());
            userData.put("dateNaissance", user.getDateNaissance());
            userData.put("numero", user.getNumero());
            userData.put("adresse", user.getAdresse());
            userData.put("role", user.getRole());
            
            return ResponseEntity.ok(userData);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", "Erreur lors de la récupération des informations utilisateur: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/me/ouvrier")
    public ResponseEntity<Map<String, Object>> getCurrentOuvrier() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            Ouvrier ouvrier = ouvrierRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Ouvrier non trouvé"));
            
            Map<String, Object> ouvrierData = new HashMap<>();
            ouvrierData.put("trackingId", ouvrier.getTrackingId());
            ouvrierData.put("nom", ouvrier.getNom());
            ouvrierData.put("prenom", ouvrier.getPrenom());
            ouvrierData.put("email", ouvrier.getEmail());
            ouvrierData.put("cni", ouvrier.getCni());
            ouvrierData.put("dateNaissance", ouvrier.getDateNaissance());
            ouvrierData.put("numero", ouvrier.getNumero());
            ouvrierData.put("adresse", ouvrier.getAdresse());
            ouvrierData.put("role", ouvrier.getRole());
            ouvrierData.put("specialite", ouvrier.getSpecialite());
            ouvrierData.put("serviceId", ouvrier.getServiceId());
            ouvrierData.put("signalementActuelId", ouvrier.getSignalementActuelId());
            
            return ResponseEntity.ok(ouvrierData);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", "Erreur lors de la récupération des informations ouvrier: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}