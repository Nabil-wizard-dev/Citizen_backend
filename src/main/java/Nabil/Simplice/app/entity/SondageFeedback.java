package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reponse d'un citoyen a un sondage.
 *
 * Un sondage recoit plusieurs reponses ; un citoyen ne peut en deposer qu'une seule
 * par sondage (contrainte d'unicite sondage_id + citoyen_id).
 */
@Entity
@Table(name = "sondage_feedbacks",
       uniqueConstraints = @UniqueConstraint(
               name = "uk_feedback_sondage_citoyen",
               columnNames = {"sondage_id", "citoyen_id"}),
       indexes = @Index(name = "idx_feedback_sondage", columnList = "sondage_id"))
@Check(name = "ck_feedback_note", constraints = "note is null or (note between 1 and 5)")
public class SondageFeedback extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    /** Le sondage auquel cette reponse se rattache. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sondage_id", nullable = false, updatable = false)
    private Sondage sondage;

    /**
     * Le citoyen qui repond.
     * Type Utilisateur et non Citoyen : les comptes crees via /api/auth/register portent
     * le discriminant de base, un mapping vers Citoyen les rendrait inutilisables ici.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "citoyen_id", nullable = false, updatable = false)
    private Utilisateur citoyen;

    /** Reponse a la question fermee du sondage : les travaux sont-ils confirmes ? */
    @Column(nullable = false)
    private Boolean confirmation;

    /** Facultative : on peut confirmer sans noter. */
    @Column
    @Min(1) @Max(5)
    private Integer note;

    /** Facultatif : on peut noter sans commenter. */
    @Column(length = 500)
    private String commentaire;

    /**
     * Horodatage precis de la reponse.
     * AuditTable.createDate est un LocalDate : il ne permet pas d'ordonner deux reponses
     * deposees le meme jour.
     */
    @Column(name = "date_reponse", nullable = false, updatable = false)
    private LocalDateTime dateReponse;

    // constructeurs

    public SondageFeedback() {
        super();
    }

    /** Filet de securite : evite une insertion invalide si le mapper oublie un de ces deux champs. */
    @PrePersist
    protected void avantEnregistrement() {
        if (trackingId == null) {
            trackingId = UUID.randomUUID();
        }
        if (dateReponse == null) {
            dateReponse = LocalDateTime.now();
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

    public Sondage getSondage() {
        return sondage;
    }

    public void setSondage(Sondage sondage) {
        this.sondage = sondage;
    }

    public Utilisateur getCitoyen() {
        return citoyen;
    }

    public void setCitoyen(Utilisateur citoyen) {
        this.citoyen = citoyen;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDateTime getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDateTime dateReponse) {
        this.dateReponse = dateReponse;
    }
}
