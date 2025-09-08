package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.entity.Notification;
import org.springframework.stereotype.Component;

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
        return notif;
    }

    public NotificationResponse toResponse(Notification notif){
        NotificationResponse notifRes = new NotificationResponse();
        notifRes.setDestinataireId(notif.getDestinataireId());
        notifRes.setMessage(notif.getMessage());
        notifRes.setLu(notif.isLu());
        notifRes.setEntiteId(notif.getEntiteId());
        notifRes.setTrackingId(notif.getTrackingId());
        return notifRes;
    }
}
