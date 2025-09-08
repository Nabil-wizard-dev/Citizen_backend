package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    

    Optional<List<Notification>> findByDestinataireId(UUID destinataireId);
    Optional<List<Notification>> findByEntiteId(UUID entiteId);

    Optional<Notification> findByTrackingId(UUID trackingId);

    boolean existsByTrackingId(UUID trackingId);
}
