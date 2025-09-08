package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {
    
    Optional<Rapport> findByTrackingId(UUID trackingId);
    
    @Query("SELECT r FROM Rapport r WHERE r.signalement.id = :signalementId")
    List<Rapport> findBySignalementId(@Param("signalementId") Long signalementId);
    
    @Query("SELECT r FROM Rapport r WHERE r.signalement.trackingId = :signalementTrackingId")
    List<Rapport> findBySignalementTrackingId(@Param("signalementTrackingId") UUID signalementTrackingId);
}