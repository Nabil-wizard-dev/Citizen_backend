package Nabil.Simplice.app.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public class AnnonceRequest {
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;
    private String Message;
    private List<String> FichierPaths;
    private UUID EntiteId;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<String> getFichierPaths() {
        return FichierPaths;
    }

    public void setFichierPaths(List<String> fichierPaths) {
        FichierPaths = fichierPaths;
    }

    public UUID getEntiteId() {
        return EntiteId;
    }

    public void setEntiteId(UUID entiteId) {
        EntiteId = entiteId;
    }
}
