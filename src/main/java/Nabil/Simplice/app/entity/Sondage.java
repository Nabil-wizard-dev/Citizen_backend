package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.enums.StatutSondage;
import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Sondage ouvert par la mairie sur un signalement traite.
 *
 * Un signalement ne porte qu'un seul sondage (contrainte d'unicite sur signalement_id).
 *
 * Les compteurs nb_oui / nb_non / nb_notes / somme_notes sont denormalises : ils sont
 * maintenus a chaque reponse pour que l'affichage des resultats ne declenche aucune
 * agregation. resultat et moyenne_finale sont figes au moment de la cloture.
 */
@Entity
@Table(name = "sondages",
       uniqueConstraints = @UniqueConstraint(name = "uk_sondage_signalement", columnNames = "signalement_id"))
public class Sondage extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, length = 1000)
    private String description;

    /** Le signalement sur lequel porte le sondage. Un seul sondage par signalement. */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "signalement_id", nullable = false, updatable = false, unique = true)
    private Signalement signalement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private StatutSondage statut;

    @Column(name = "date_ouverture", nullable = false, updatable = false)
    private LocalDateTime dateOuverture;

    /** Renseignee uniquement a la cloture. */
    @Column(name = "date_cloture")
    private LocalDateTime dateCloture;

    // ---- compteurs denormalises, maintenus par le service des reponses ----

    @Column(name = "nb_oui", nullable = false)
    private int nbOui = 0;

    @Column(name = "nb_non", nullable = false)
    private int nbNon = 0;

    @Column(name = "nb_notes", nullable = false)
    private int nbNotes = 0;

    @Column(name = "somme_notes", nullable = false)
    private int sommeNotes = 0;

    // ---- resultats figes a la cloture ----

    /** true si les oui l'emportent, false si les non l'emportent, null en cas d'egalite ou d'absence de reponse. */
    @Column(name = "resultat")
    private Boolean resultat;

    @Column(name = "moyenne_finale")
    private Double moyenneFinale;

    // constructeurs

    public Sondage() {
        super();
    }

    /** Filet de securite : un sondage est toujours cree ouvert et horodate. */
    @PrePersist
    protected void avantEnregistrement() {
        if (trackingId == null) {
            trackingId = UUID.randomUUID();
        }
        if (statut == null) {
            statut = StatutSondage.OUVERT;
        }
        if (dateOuverture == null) {
            dateOuverture = LocalDateTime.now();
        }
    }

    // getters et setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Signalement getSignalement() {
        return signalement;
    }

    public void setSignalement(Signalement signalement) {
        this.signalement = signalement;
    }

    public StatutSondage getStatut() {
        return statut;
    }

    public void setStatut(StatutSondage statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDateTime dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDateTime getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public int getNbOui() {
        return nbOui;
    }

    public void setNbOui(int nbOui) {
        this.nbOui = nbOui;
    }

    public int getNbNon() {
        return nbNon;
    }

    public void setNbNon(int nbNon) {
        this.nbNon = nbNon;
    }

    public int getNbNotes() {
        return nbNotes;
    }

    public void setNbNotes(int nbNotes) {
        this.nbNotes = nbNotes;
    }

    public int getSommeNotes() {
        return sommeNotes;
    }

    public void setSommeNotes(int sommeNotes) {
        this.sommeNotes = sommeNotes;
    }

    public Boolean getResultat() {
        return resultat;
    }

    public void setResultat(Boolean resultat) {
        this.resultat = resultat;
    }

    public Double getMoyenneFinale() {
        return moyenneFinale;
    }

    public void setMoyenneFinale(Double moyenneFinale) {
        this.moyenneFinale = moyenneFinale;
    }
}
