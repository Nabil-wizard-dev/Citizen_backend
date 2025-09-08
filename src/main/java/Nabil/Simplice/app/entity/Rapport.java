package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rapports")
public class Rapport extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID trackingId;

    @Column(nullable = false)
    private String commentaire;

    @Column(nullable = false)
    private String filePath; // Chemin vers le fichier PDF

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signalement_id")
    private Signalement signalement;

    public Rapport() {
        super();
    }

    public Rapport(String commentaire, String filePath) {
        this.trackingId = UUID.randomUUID();
        this.commentaire = commentaire;
        this.filePath = filePath;
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Signalement getSignalement() {
        return signalement;
    }

    public void setSignalement(Signalement signalement) {
        this.signalement = signalement;
    }
}