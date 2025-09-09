package Nabil.Simplice.app.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import Nabil.Simplice.app.enums.UtilisateurRole;


public class LoginResponse {
	
	@JsonProperty("token")
	private String token;
	@JsonProperty("expiresIn")
	private Long expiresIn;
	private UUID trackingId;
	private String nom;
	private String prenom;
	private String email;
	private int numero;
	private UtilisateurRole role;
	private String adresse;
	private String cni;
	private String dateNaissance;

	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public UUID getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(UUID trackingId) {
		this.trackingId = trackingId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public UtilisateurRole getRole() {
		return role;
	}
	public void setRole(UtilisateurRole role) {
		this.role = role;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCni() {
		return cni;
	}
	public void setCni(String cni) {
		this.cni = cni;
	}
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	

	


}
