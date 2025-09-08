package Nabil.Simplice.app.dto.response;

import Nabil.Simplice.app.enums.ExtensionFichier;
import java.time.LocalDateTime;

public class FichierJoinResponse {
    private Long id;
    private String nom;
    private String description;
    private String url;
    private ExtensionFichier extension;
    private Long taille;
    private LocalDateTime dateCreation;
    private Long signalementId;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getSignalementId() {
        return signalementId;
    }

    public void setSignalementId(Long signalementId) {
        this.signalementId = signalementId;
    }
}
