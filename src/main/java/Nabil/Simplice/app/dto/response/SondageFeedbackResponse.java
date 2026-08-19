package Nabil.Simplice.app.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class SondageFeedbackResponse {

    private UUID trackingId;

    private UUID sondageId;

    private Integer note;

    private String commentaire;

    private Boolean confirmation;

    private LocalDateTime dateReponse;

    // identite de l'auteur, pour l'affichage cote tableau de bord
    private UUID auteurId;

    private String auteurNom;

    private String auteurPrenom;

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

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }

    public LocalDateTime getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDateTime dateReponse) {
        this.dateReponse = dateReponse;
    }

    public UUID getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(UUID auteurId) {
        this.auteurId = auteurId;
    }

    public String getAuteurNom() {
        return auteurNom;
    }

    public void setAuteurNom(String auteurNom) {
        this.auteurNom = auteurNom;
    }

    public String getAuteurPrenom() {
        return auteurPrenom;
    }

    public void setAuteurPrenom(String auteurPrenom) {
        this.auteurPrenom = auteurPrenom;
    }
}
