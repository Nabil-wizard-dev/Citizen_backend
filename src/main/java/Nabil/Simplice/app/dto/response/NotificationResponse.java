package Nabil.Simplice.app.dto.response;

import Nabil.Simplice.app.enums.TypeNotification;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationResponse {
    private UUID TrackingId;

    private String Message;

    private boolean lu;

    private UUID entiteId;

    private UUID destinataireId;

    private TypeNotification type;

    /** Horodatage a la seconde : permet au front d'afficher un "il y a 2 h". */
    private LocalDateTime dateCreation;

    public UUID getTrackingId() {
        return TrackingId;
    }

    public void setTrackingId(UUID trackingId) {
        TrackingId = trackingId;
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
