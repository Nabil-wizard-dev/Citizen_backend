package Nabil.Simplice.app.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Reponse envoyee par un utilisateur a un sondage.
 * L'auteur n'est pas dans la requete : il est deduit du token, pour qu'on ne puisse pas
 * repondre a la place de quelqu'un d'autre.
 */
public class SondageFeedbackRequest {

    @NotNull(message = "L'identifiant du sondage est obligatoire")
    private UUID sondageId;

    @NotNull(message = "La note est obligatoire")
    @Min(value = 1, message = "La note doit être comprise entre 1 et 5")
    @Max(value = 5, message = "La note doit être comprise entre 1 et 5")
    private Integer note;

    @Size(max = 500, message = "Le commentaire ne peut pas dépasser 500 caractères")
    private String commentaire;

    /** Facultatif : null signifie que l'utilisateur n'a pas répondu à cette question. */
    private Boolean confirmation;

    // getters et setters

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
}
