package Nabil.Simplice.app.entity;

import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity @Table(name = "Notifications")
public class Notification extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    protected UUID trackingId;

    @Column(nullable = false)
    private String Message;

    @Column()
    private boolean lu;

    @Column()
    private UUID entiteId;

    @Column()
    private UUID destinataireId;

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
}
