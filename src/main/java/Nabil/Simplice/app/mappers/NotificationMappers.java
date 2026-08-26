package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.entity.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class NotificationMappers {
    public Notification toEntity(NotificationRequest req){
        Notification notif = new Notification();
        notif.setTrackingId(UUID.randomUUID());
        notif.setMessage(req.getMessage());
        notif.setLu(req.isLu());
        notif.setDestinataireId(req.getDestinataireId());
        notif.setEntiteId(req.getEntiteId());
        notif.setType(req.getType());
        return notif;
    }

    public NotificationResponse toResponse(Notification notif){
        NotificationResponse notifRes = new NotificationResponse();
        notifRes.setDestinataireId(notif.getDestinataireId());
        notifRes.setMessage(notif.getMessage());
        notifRes.setLu(notif.isLu());
        notifRes.setEntiteId(notif.getEntiteId());
        notifRes.setTrackingId(notif.getTrackingId());
        notifRes.setType(notif.getType());
        notifRes.setDateCreation(horodatage(notif));
        return notifRes;
    }

    /**
     * Les notifications creees avant l'ajout de la colonne date_creation n'ont que la date
     * d'audit, au jour pres : on la ramene a minuit plutot que de renvoyer null au front.
     */
    private LocalDateTime horodatage(Notification notif) {
        if (notif.getDateCreation() != null) {
            return notif.getDateCreation();
        }
        return notif.getCreateDate() != null ? notif.getCreateDate().atStartOfDay() : null;
    }
}
