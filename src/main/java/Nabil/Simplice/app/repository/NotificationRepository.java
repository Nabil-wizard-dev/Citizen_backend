package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // tri sur l'horodatage, pas sur l'id : l'ordre affiche doit coller aux dates
    // que le front rend en "il y a 2 h". L'id departage les ex aequo, et les lignes
    // sans date_creation (anterieures a la colonne) partent en fin de liste.
    List<Notification> findByDestinataireIdOrderByDateCreationDescIdDesc(UUID destinataireId);

    List<Notification> findByEntiteIdOrderByDateCreationDescIdDesc(UUID entiteId);

    long countByDestinataireIdAndLuFalse(UUID destinataireId);

    /** Marque d'un coup toutes les notifications non lues d'un destinataire (bouton "tout marquer comme lu"). */
    @Modifying(clearAutomatically = true)
    @Query("update Notification n set n.lu = true where n.destinataireId = :destinataireId and n.lu = false")
    int marquerToutesLues(@Param("destinataireId") UUID destinataireId);

    Optional<Notification> findByTrackingId(UUID trackingId);

    boolean existsByTrackingId(UUID trackingId);
}
