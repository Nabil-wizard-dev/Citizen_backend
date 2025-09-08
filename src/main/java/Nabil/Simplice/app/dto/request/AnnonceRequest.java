package Nabil.Simplice.app.dto.request;

import java.util.List;
import java.util.UUID;

public class AnnonceRequest {
    private String Message;
    private List<String> FichierPaths;
    private UUID EntiteId;

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
