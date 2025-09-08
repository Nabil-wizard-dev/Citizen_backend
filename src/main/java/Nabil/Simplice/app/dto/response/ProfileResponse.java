package Nabil.Simplice.app.dto.response;

import Nabil.Simplice.app.enums.UtilisateurRole;
import java.util.UUID;

public class ProfileResponse {
    private UUID trackingId;
    private String nom;
    private String prenom;
    private String email;
    private int numero;
    private String adresse;
    private String dateNaissance;
    private String cni;
    private UtilisateurRole role;
    private String photoProfil;

    public ProfileResponse() {
    }

    public ProfileResponse(UUID trackingId, String nom, String prenom, String email, int numero, String adresse, String dateNaissance, String cni, UtilisateurRole role, String photoProfil) {
        this.trackingId = trackingId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numero = numero;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.cni = cni;
        this.role = role;
        this.photoProfil = photoProfil;
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

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public UtilisateurRole getRole() {
        return role;
    }

    public void setRole(UtilisateurRole role) {
        this.role = role;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }
} 