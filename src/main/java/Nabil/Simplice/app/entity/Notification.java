package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.enums.TypeNotification;
import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Table(name = "Notifications")
public class Notification extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    protected UUID trackingId;

    // 500 caracteres : un message reprend le titre du signalement (jusqu'a 255)
    // en plus de son propre libelle, ce qui deborde la longueur par defaut
    @Column(nullable = false, length = 500)
    private String Message;

    @Column()
    private boolean lu;

    @Column()
    private UUID entiteId;

    @Column()
    private UUID destinataireId;

    /** Nature de l'entite visee : permet au front de router le clic sur la notification. */
    // varchar et non enum MySQL : ajouter une valeur a TypeNotification ne doit pas
    // exiger un ALTER TABLE, ddl-auto=update ne modifiant jamais une colonne existante
    @Enumerated(EnumType.STRING)
    @Column(length = 20, columnDefinition = "varchar(20)")
    private TypeNotification type;

    /**
     * Horodatage a la seconde, la ou createDate de AuditTable n'a qu'une precision au jour.
     * C'est ce champ qui permet au front d'afficher "il y a 2 h".
     *
     * Colonne nullable a dessein : ddl-auto=update ajouterait une colonne NOT NULL sans
     * valeur par defaut sur une table deja peuplee, ce que MySQL refuse en mode strict.
     * Le @PrePersist ci-dessous garantit qu'aucune nouvelle ligne ne sort sans horodatage.
     */
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    /** Filet de securite : toute notification est horodatee et identifiee a l'enregistrement. */
    @PrePersist
    protected void avantEnregistrement() {
        if (trackingId == null) {
            trackingId = UUID.randomUUID();
        }
        if (dateCreation == null) {
            dateCreation = LocalDateTime.now();
        }
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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public UUID getEntiteId() {
        return entiteId;
    }

    public void setEntiteId(UUID entiteId) {
        this.entiteId = entiteId;
    }

    public UUID getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(UUID destinataireId) {
        this.destinataireId = destinataireId;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public TypeNotification getType() {
        return type;
    }

    public void setType(TypeNotification type) {
        this.type = type;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
