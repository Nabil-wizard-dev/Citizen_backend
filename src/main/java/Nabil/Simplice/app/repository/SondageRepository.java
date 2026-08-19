package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Annonce;
import Nabil.Simplice.app.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SondageRepository extends JpaRepository<Sondage, Long> {


//    List<Sondage> findByTrackingId(UUID trackingId);

    boolean existsSondageByTrackingId(UUID trackingId);

//  recuperer le sondage a travers au signalement au quel il est relier
    @Query("select s from Sondage s where s.entiteId = ?1 ")
    List<Sondage> findAllByEntiteId(UUID entiteId);

    Optional<Sondage> findByTrackingId(UUID trackingId);

}
