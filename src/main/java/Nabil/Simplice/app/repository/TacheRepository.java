package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {

    Optional<Tache> findByTrakingId(UUID trakingId);

    @Query("select t from Tache t where t.signalement.trackingId = ?1")
    Optional<Tache> findBySignalementId(UUID signalementId);

    boolean existsByTrakingId(UUID trakingId);

}
