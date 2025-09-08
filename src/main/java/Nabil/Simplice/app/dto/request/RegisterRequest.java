package Nabil.Simplice.app.dto.request;

import Nabil.Simplice.app.enums.UtilisateurRole;
import jakarta.validation.constraints.*;

public class RegisterRequest {
	@NotBlank(message = "Le nom est obligatoire")
	@Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
	private String nom;
	
	@NotBlank(message = "Le prénom est obligatoire")
	@Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
	private String prenom;
	
	@NotBlank(message = "Le CNI est obligatoire")
	@Pattern(regexp = "^[A-Z0-9]{8,15}$", message = "Format CNI invalide")
	private String cni;
	
	@NotBlank(message = "La date de naissance est obligatoire")
	private String dateNaissance;
	
	@NotBlank(message = "L'email est obligatoire")
	@Email(message = "Format email invalide")
	private String email;
	
	@NotBlank(message = "Le mot de passe est obligatoire")
	@Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
	private String motDePasse;
	
	@Positive(message = "Le numéro doit être positif")
	private int numero;
	
	@NotBlank(message = "L'adresse est obligatoire")
	private String adresse;
	
	@NotNull(message = "Le rôle est obligatoire")
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

