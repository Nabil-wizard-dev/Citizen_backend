package Nabil.Simplice.app.dto.response;

import Nabil.Simplice.app.entity.Annonce;

import java.util.List;
import java.util.UUID;

public class AnnonceResponse {
    private UUID trackingId;
    private String Message;
    private List<String> FichierPaths;
    private UUID EntiteId;

    public UUID getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
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
