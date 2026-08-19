package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Ouvrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.enums.StatutSignalement;


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

    // ---- regroupement par dossier (code SIG-xxxx) ----

    boolean existsByCode(String code);

    /** Tous les signalements d'un dossier, le plus ancien (l'ancre) en premier. */
    List<Signalement> findByCodeOrderByIdAsc(String code);

    /**
     * Ancres candidates au regroupement : presélection par rectangle englobant.
     * Bien plus rapide qu'un calcul de distance sur toute la table, et suffisant
     * avant le filtrage précis par Haversine côté Java.
     */
    @Query("select s from Signalement s "
         + "where s.latitude is not null and s.longitude is not null "
         + "and s.latitude between :latMin and :latMax "
         + "and s.longitude between :lngMin and :lngMax "
         + "and s.statut in :statuts "
         + "order by s.id asc")
    List<Signalement> findCandidatsDansZone(@Param("latMin") double latMin,
                                            @Param("latMax") double latMax,
                                            @Param("lngMin") double lngMin,
                                            @Param("lngMax") double lngMax,
                                            @Param("statuts") List<StatutSignalement> statuts);
}
