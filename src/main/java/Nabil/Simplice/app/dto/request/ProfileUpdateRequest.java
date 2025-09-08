package Nabil.Simplice.app.dto.request;

import Nabil.Simplice.app.enums.UtilisateurRole;

public class ProfileUpdateRequest {
    private String nom;
    private String prenom;
    private String email;
    private int numero;
    private String adresse;
    private String dateNaissance;
    private String photoProfil;

    public ProfileUpdateRequest() {
    }

    public ProfileUpdateRequest(String nom, String prenom, String email, int numero, String adresse, String dateNaissance, String photoProfil) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numero = numero;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.photoProfil = photoProfil;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }
} 