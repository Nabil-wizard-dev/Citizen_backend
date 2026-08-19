package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reponse d'un utilisateur a un sondage.
 *
 * Un sondage recoit plusieurs feedbacks ; un utilisateur ne peut en deposer qu'un seul
 * par sondage (contrainte d'unicite sondage_id + utilisateur_id).
 */
@Entity
@Table(name = "sondage_feedbacks",
       uniqueConstraints = @UniqueConstraint(
               name = "uk_feedback_sondage_utilisateur",
               columnNames = {"sondage_id", "utilisateur_id"}),
       indexes = @Index(name = "idx_feedback_sondage", columnList = "sondage_id"))
public class SondageFeedback extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    /**
     * Reponse a la question fermee du sondage.
     * Objet et non primitif : null signifie "l'utilisateur n'a pas repondu",
     * ce qui n'est pas la meme chose que false.
     */
    @Column
    private Boolean confirmation;

    /** Note de 1 a 5. Integer et non int : 0 n'est pas une reponse valide. */
    @Column(nullable = false)
    @NotNull @Min(1) @Max(5)
    private Integer note;

    /** Facultatif : on peut noter sans commenter. */
    @Column(length = 500)
    private String commentaire;

    /** Le sondage auquel cette reponse se rattache. Plusieurs feedbacks pour un meme sondage. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sondage_id", nullable = false, updatable = false)
    private Sondage sondage;

    /**
     * L'utilisateur qui repond.
     * Type Utilisateur et non Citoyen : les comptes crees via /api/auth/register portent
     * le discriminant de base, un mapping vers Citoyen les rendrait inutilisables ici.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false, updatable = false)
    private Utilisateur utilisateur;

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

    public Sondage getSondage() {
        return sondage;
    }

    public void setSondage(Sondage sondage) {
        this.sondage = sondage;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDateTime getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDateTime dateReponse) {
        this.dateReponse = dateReponse;
    }
}
