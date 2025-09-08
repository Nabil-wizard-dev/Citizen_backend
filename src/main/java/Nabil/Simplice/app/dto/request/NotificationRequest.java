package Nabil.Simplice.app.dto.request;

import jakarta.persistence.Column;

import java.util.UUID;

public class NotificationRequest {

    private String Message;

    private boolean lu;

    private UUID entiteId;

    private UUID destinataireId;

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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
}
