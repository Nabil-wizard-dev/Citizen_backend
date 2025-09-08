package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "taches")
public class Tache extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID trakingId;

    @Column()
    private Date dateDebut;

    @Column()
    private Date dateFin;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] fichierDevis;

    @Column()
    private boolean isActiver;

    @Column()
    private boolean isResolu;

//    @OneToOne(mappedBy = "tache")
//    private Signalement signalement;

    @OneToOne
    @JoinColumn(name = "signalement_id")
    private Signalement signalement;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true) // Mappé par le champ "tache" dans EtatDeTache
    private List<EtatDeTache> etatsDeTache = new ArrayList<>();


    //get et set

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getTrakingId() {
        return trakingId;
    }

    public void setTrakingId(UUID trakingId) {
        this.trakingId = trakingId;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isActiver() {
        return isActiver;
    }

    public void setActiver(boolean activer) {
        isActiver = activer;
    }

    public boolean isResolu() {
        return isResolu;
    }

    public void setResolu(boolean resolu) {
        isResolu = resolu;
    }

    public Signalement getSignalement() {
        return signalement;
    }

    public void setSignalement(Signalement signalement) {
        this.signalement = signalement;
    }

    public List<EtatDeTache> getEtatsDeTache() {
        return etatsDeTache;
    }

    public void setEtatsDeTache(List<EtatDeTache> etatsDeTache) {
        this.etatsDeTache = etatsDeTache;
    }

    public byte[] getFichierDevis() {
        return fichierDevis;
    }

    public void setFichierDevis(byte[] fichierDevis) {
        this.fichierDevis = fichierDevis;
    }
}
