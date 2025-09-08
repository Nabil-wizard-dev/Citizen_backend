package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity @Table(name = "annonces")
public class Annonce extends AuditTable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID trackingId;

    @Column(nullable = false)
    private String Message;

    // pour stocker les images concernants l'annoces
    @ElementCollection
    @CollectionTable(name = "fichiers_annonce_paths", joinColumns = @JoinColumn(name = "annonce_id"))
    @Column(name = "fichier_annonce_path")
    private List<String> fichiersPaths = new ArrayList<>();

    // ds le cas ou l'annonce concerne un signalement
    @Column()
    private UUID entiteId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<String> getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(List<String> fichiersPaths) {
        this.fichiersPaths = fichiersPaths;
    }

    public UUID getEntiteId() {
        return entiteId;
    }

    public void setEntiteId(UUID entiteId) {
        this.entiteId = entiteId;
    }
}
