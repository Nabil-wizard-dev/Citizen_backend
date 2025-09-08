package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "devis")
public class Devis extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID trackingId;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String filePath; // Chemin vers le fichier PDF

    @Column(name = "date_creation_devis")
    private String dateCreationDevis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signalement_id")
    private Signalement signalement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ouvrier_id")
    private Ouvrier ouvrier;

    public Devis() {
        super();
    }

    public Devis(String titre, String filePath, String dateCreationDevis) {
        this.trackingId = UUID.randomUUID();
        this.titre = titre;
        this.filePath = filePath;
        this.dateCreationDevis = dateCreationDevis;
    }

    // Getters and Setters
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDateCreationDevis() {
        return dateCreationDevis;
    }

    public void setDateCreationDevis(String dateCreationDevis) {
        this.dateCreationDevis = dateCreationDevis;
    }

    public Signalement getSignalement() {
        return signalement;
    }

    public void setSignalement(Signalement signalement) {
        this.signalement = signalement;
    }

    public Ouvrier getOuvrier() {
        return ouvrier;
    }

    public void setOuvrier(Ouvrier ouvrier) {
        this.ouvrier = ouvrier;
    }
}