package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
    
    Optional<Devis> findByTrackingId(UUID trackingId);
    
    @Query("SELECT d FROM Devis d WHERE d.signalement.id = :signalementId")
    List<Devis> findBySignalementId(@Param("signalementId") Long signalementId);
    
    @Query("SELECT d FROM Devis d WHERE d.signalement.trackingId = :signalementTrackingId")
    List<Devis> findBySignalementTrackingId(@Param("signalementTrackingId") UUID signalementTrackingId);
}