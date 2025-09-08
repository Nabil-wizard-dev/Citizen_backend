package Nabil.Simplice.app.entity;

import java.time.Duration;
import java.util.UUID;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity @Table(name = "fichierJoins")
public class FichierJoin extends AuditTable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private UUID trackingId;
    //    @Column(nullable = false)
    protected String code;
    //    @Column(nullable = false)
    protected String taille;
    @Column(nullable = false,unique = true)
    protected String chemin;
    //    @Column(nullable = false)
    private  Duration duree; // pour video et audio
    @Column(nullable = false)
//  @Enumerated(EnumType.STRING)
    protected String extension;
    // signalement

    @ManyToOne
    @JoinColumn(name = "signalement_id")
    private Signalement signalement;

    @ManyToOne
    @JoinColumn(name = "etatDeTache_id")
    private EtatDeTache etatDeTache;


    // constructeur

    public FichierJoin( UUID trackingId, String code, String taille, String chemin, Duration duree,
                        String extension) {
        super();
        this.trackingId = trackingId;
        this.code = code;
        this.taille = taille;
        this.chemin = chemin;
        this.duree = duree;
        this.extension = extension;
    }


    public FichierJoin() {
        super();
        // TODO Auto-generated constructor stub
    }



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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getTaille() {
        return taille;
    }
    public void setTaille(String taille) {
        this.taille = taille;
    }
    public String getChemin() {
        return chemin;
    }
    public void setChemin(String chemin) {
        this.chemin = chemin;
    }
    public Duration getDuree() {
        return duree;
    }
    public void setDuree(Duration duree) {
        this.duree = duree;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Signalement getSignalement() {
        return signalement;
    }

    public void setSignalement(Signalement signalement) {
        this.signalement = signalement;
    }

    public EtatDeTache getEtatDeTache() {
        return etatDeTache;
    }

    public void setEtatDeTache(EtatDeTache etatDeTache) {
        this.etatDeTache = etatDeTache;
    }
}
