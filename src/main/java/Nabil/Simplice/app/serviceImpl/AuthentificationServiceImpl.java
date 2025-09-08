package Nabil.Simplice.app.serviceImpl;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Nabil.Simplice.app.dto.request.LoginRequest;
import Nabil.Simplice.app.dto.request.RegisterRequest;
import Nabil.Simplice.app.dto.response.LoginResponse;
import Nabil.Simplice.app.dto.response.RegisterResponse;
import Nabil.Simplice.app.entity.Utilisateur;
import Nabil.Simplice.app.repository.UtilisateurRepository;
import Nabil.Simplice.app.service.AuthentificationService;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthentificationServiceImpl(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Utilisateur user = new Utilisateur();
        user.setTrackingId(UUID.randomUUID());
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setRole(request.getRole());
        user.setNumero(request.getNumero());
        user.setDateNaissance(request.getDateNaissance());
        user.setAdresse(request.getAdresse());
        user.setCni(request.getCni());
        
        Utilisateur savedUser = utilisateurRepository.save(user);
        
        RegisterResponse response = new RegisterResponse();
        response.setTrackingId(savedUser.getTrackingId());
        response.setNom(savedUser.getNom());
        response.setPrenom(savedUser.getPrenom());
        response.setEmail(savedUser.getEmail());
        response.setMotDePasse(savedUser.getMotDePasse());
        response.setRole(savedUser.getRole());
        response.setNumero(savedUser.getNumero());
        response.setDateNaissance(savedUser.getDateNaissance());
        response.setAdresse(savedUser.getAdresse());
        response.setCni(savedUser.getCni());
        
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse()));
        
        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe invalide"));
        
        String jwt = jwtService.generateToken(user);
        
        LoginResponse response = new LoginResponse();
        response.setToken(jwt);
        response.setExpiresIn(3600L); // 1 heure en secondes
        response.setRole(user.getRole());
        return response;
    }

    
}
