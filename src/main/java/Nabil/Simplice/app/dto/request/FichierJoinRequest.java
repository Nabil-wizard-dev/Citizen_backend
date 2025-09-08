package Nabil.Simplice.app.dto.request;

import java.time.Duration;
import java.util.UUID;

import Nabil.Simplice.app.enums.ExtensionFichier;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class FichierJoinRequest {
    private String nom;
    private String description;
    private String url;
    private ExtensionFichier extension;
    private Long taille;
    private Long signalementId;

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ExtensionFichier getExtension() {
        return extension;
    }

    public void setExtension(ExtensionFichier extension) {
        this.extension = extension;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Long getSignalementId() {
        return signalementId;
    }

    public void setSignalementId(Long signalementId) {
        this.signalementId = signalementId;
    }
}

