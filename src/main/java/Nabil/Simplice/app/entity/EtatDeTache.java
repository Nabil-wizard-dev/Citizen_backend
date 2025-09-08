package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "etatDeTaches")
public class EtatDeTache extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID trackingId;
    @Column()
    private String description;

    @ElementCollection
    @CollectionTable(name = "etatdetache_fichiers_paths", joinColumns = @JoinColumn(name = "etatdetache_id"))
    @Column(name = "fichier_path")
    private List<String> fichiersPaths = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) // Many instances of EtatDeTache related to One Tache
    @JoinColumn(name = "tache_id", nullable = false) // Foreign key in the EtatDeTache table
    private Tache tache;


    //get et set

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(List<String> fichiersPaths) {
        this.fichiersPaths = fichiersPaths;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

}
