package Nabil.Simplice.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class SondageRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;

    /** Le signalement sur lequel porte le sondage. Ignoré lors d'une mise à jour. */
    @NotNull(message = "L'identifiant du signalement est obligatoire")
    private UUID signalementId;

    // getters et setters

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getSignalementId() {
        return signalementId;
    }

    public void setSignalementId(UUID signalementId) {
        this.signalementId = signalementId;
    }
}
