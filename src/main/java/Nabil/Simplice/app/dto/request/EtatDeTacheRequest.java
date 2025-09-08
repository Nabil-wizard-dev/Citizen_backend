package Nabil.Simplice.app.dto.request;

import java.util.List;
import java.util.UUID;

public class EtatDeTacheRequest {

    private String description;

    // Note: Ancienne gestion des fichiers par UUID supprimée
    // private List<UUID> fichiers ;

    private List<String> fichiersPaths;

    private UUID tache;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Méthodes getFichiers et setFichiers supprimées

    public UUID getTache() {
        return tache;
    }

    public void setTache(UUID tache) {
        this.tache = tache;
    }

    public List<String> getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(List<String> fichierPath) {
        this.fichiersPaths = fichierPath;
    }
}
