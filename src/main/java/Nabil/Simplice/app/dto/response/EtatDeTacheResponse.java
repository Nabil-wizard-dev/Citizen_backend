package Nabil.Simplice.app.dto.response;

import java.util.List;
import java.util.UUID;

public class EtatDeTacheResponse {
    private UUID trackingId;

    private String description;

    private List<UUID> fichiers ;

    private List<String> fichiersPaths;

    private UUID tache;

    public UUID getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UUID> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<UUID> fichiers) {
        this.fichiers = fichiers;
    }

    public UUID getTache() {
        return tache;
    }

    public void setTache(UUID tache) {
        this.tache = tache;
    }

    public List<String> getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(List<String> fichiersPaths) {
        this.fichiersPaths = fichiersPaths;
    }
}
