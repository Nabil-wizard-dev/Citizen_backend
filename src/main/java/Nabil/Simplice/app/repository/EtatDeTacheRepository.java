package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.EtatDeTache;
import Nabil.Simplice.app.entity.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EtatDeTacheRepository extends JpaRepository<EtatDeTache, Long> {

    Optional<EtatDeTache> findByTrackingId(UUID trackingId);

//    @Query("select t from Tache t where t.signalement.trackingId = ?1")
//    Optional<Tache> findBySignalementId(UUID signalementId);

    @Query("select e from EtatDeTache e where e.tache.trakingId = ?1")
    List<EtatDeTache> getAllByTacheId(UUID id);

    boolean existsByTrackingId(UUID trackingId);
}
