package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Ouvrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Nabil.Simplice.app.entity.Signalement;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SignalementRepository extends JpaRepository<Signalement, Long> {
    
    void deleteByTrackingId(UUID trackingId);

    Optional<Signalement> findByTrackingId(UUID trackingId);

    
    @Query("SELECT s FROM Signalement s WHERE s.ouvrier.trackingId = ?1")
    List<Signalement> findByOuvrierTrackingId(UUID trackingId);

    boolean existsByTrackingId(UUID id);
    // Ici, tu peux ajouter des méthodes personnalisées si besoin
}
