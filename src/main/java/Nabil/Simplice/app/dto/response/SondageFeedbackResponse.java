package Nabil.Simplice.app.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class SondageFeedbackResponse {

    private UUID trackingId;

    private UUID sondageId;

    private Boolean confirmation;

    private Integer note;

    private String commentaire;

    private LocalDateTime dateReponse;

    // identite du citoyen, pour l'affichage cote tableau de bord
    private UUID citoyenId;

    private String citoyenNom;

    private String citoyenPrenom;

    // getters et setters

    public UUID getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
    }

    public UUID getSondageId() {
        return sondageId;
    }

    public void setSondageId(UUID sondageId) {
        this.sondageId = sondageId;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDateTime getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDateTime dateReponse) {
        this.dateReponse = dateReponse;
    }

    public UUID getCitoyenId() {
        return citoyenId;
    }

    public void setCitoyenId(UUID citoyenId) {
        this.citoyenId = citoyenId;
    }

    public String getCitoyenNom() {
        return citoyenNom;
    }

    public void setCitoyenNom(String citoyenNom) {
        this.citoyenNom = citoyenNom;
    }

    public String getCitoyenPrenom() {
        return citoyenPrenom;
    }

    public void setCitoyenPrenom(String citoyenPrenom) {
        this.citoyenPrenom = citoyenPrenom;
    }
}
