package Nabil.Simplice.app.dto.response;

import java.util.UUID;

import Nabil.Simplice.app.enums.UtilisateurRole;

public class UtilisateurResponse {
    private UUID trackingId;
    private String nom;
    private String prenom;
    private String email;
    private String numero;
    private UtilisateurRole role;

    public UtilisateurResponse() {
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public UtilisateurRole getRole() {
		return role;
	}

	public void setRole(UtilisateurRole role) {
		this.role = role;
	}



} 