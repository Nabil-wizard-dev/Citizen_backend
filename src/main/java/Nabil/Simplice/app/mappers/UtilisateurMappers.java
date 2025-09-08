package Nabil.Simplice.app.mappers;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import Nabil.Simplice.app.dto.request.RegisterRequest;
import Nabil.Simplice.app.dto.response.RegisterResponse;
import Nabil.Simplice.app.entity.Utilisateur;


@Component
public class UtilisateurMappers {
	
	protected final PasswordEncoder passwordEncoder;
	
    
	public UtilisateurMappers(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}
	//toEntity
	public Utilisateur toEntity(RegisterRequest userReq) {
		Utilisateur user = new Utilisateur();
		user.setTrackingId(UUID.randomUUID());
		user.setCni(userReq.getCni());
		user.setEmail(userReq.getEmail());
		user.setMotDePasse(passwordEncoder.encode(userReq.getMotDePasse()));
		user.setNom(userReq.getNom());
		user.setRole(userReq.getRole());
		user.setPrenom(userReq.getPrenom());
		user.setNumero(userReq.getNumero());
		user.setDateNaissance(userReq.getDateNaissance());
		user.setAdresse(userReq.getAdresse());
		return user;
	}
	//toResponse
	public RegisterResponse toResponse(Utilisateur user) {
		RegisterResponse userRes = new RegisterResponse();
		userRes.setTrackingId(user.getTrackingId());
		userRes.setCni(user.getCni());
		userRes.setDateNaissance(user.getDateNaissance());
		userRes.setEmail(user.getEmail());
		userRes.setMotDePasse(user.getMotDePasse());
		userRes.setNom(user.getNom());
		userRes.setPrenom(user.getPrenom());
		userRes.setNumero(user.getNumero());
		userRes.setRole(user.getRole());
		userRes.setAdresse(user.getAdresse());
		
		return userRes;	
	}
}

