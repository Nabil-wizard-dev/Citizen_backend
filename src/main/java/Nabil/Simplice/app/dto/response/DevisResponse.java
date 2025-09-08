package Nabil.Simplice.app.dto.response;

import java.time.LocalDateTime;

public class DevisResponse {
    private String trackingId;
    private String titre;
    private String signalementId;
    private String ouvrierId;
    private String dateCreation;
    private String chemin;

    public DevisResponse() {
    }

    public DevisResponse(String trackingId, String titre, String signalementId, String ouvrierId, String dateCreation, String chemin) {
        this.trackingId = trackingId;
        this.titre = titre;
        this.signalementId = signalementId;
        this.ouvrierId = ouvrierId;
        this.dateCreation = dateCreation;
        this.chemin = chemin;
    }

    // Getters et Setters
    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSignalementId() {
        return signalementId;
    }

    public void setSignalementId(String signalementId) {
        this.signalementId = signalementId;
    }

    public String getOuvrierId() {
        return ouvrierId;
    }

    public void setOuvrierId(String ouvrierId) {
        this.ouvrierId = ouvrierId;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }
} 