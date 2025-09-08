package Nabil.Simplice.app.dto.request;

import Nabil.Simplice.app.enums.UtilisateurRole;

public class RegisterRequest {
	private String nom;
	private String prenom;
	private String cni;
	private String dateNaissance;
	private String email;
	private String motDePasse;
	private int numero;
	private String adresse;
	private UtilisateurRole role;
	
	
	public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
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


	public String getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMotDePasse() {
		return motDePasse;
	}


	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public UtilisateurRole getRole() {
		return role;
	}
	public void setRole(UtilisateurRole role) {
		this.role = role;
	}
	public String getCni() {
		return cni;
	}
	public void setCni(String cni) {
		this.cni = cni;
	}
	
	
	
	
}

