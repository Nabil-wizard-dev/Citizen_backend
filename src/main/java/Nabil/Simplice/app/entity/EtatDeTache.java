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

    @OneToMany(mappedBy = "etatDeTache", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FichierJoin> fichiers = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "fichiers_paths", joinColumns = @JoinColumn(name = "signalement_id"))
//    @Column(name = "fichier_path")
    private String fichiersPaths ;

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

    public List<FichierJoin> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<FichierJoin> fichiers) {
        this.fichiers = fichiers;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public String getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(String fichiersPaths) {
        this.fichiersPaths = fichiersPaths;
    }
}
