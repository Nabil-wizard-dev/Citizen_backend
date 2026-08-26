package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SondageRepository extends JpaRepository<Sondage, Long> {

    Optional<Sondage> findByTrackingId(UUID trackingId);

    boolean existsSondageByTrackingId(UUID trackingId);

    /**
     * Tous les sondages, le plus recent en premier.
     * Le "join fetch" charge le signalement dans la meme requete : sans lui, exposer
     * le signalementId de chaque sondage declenche une requete par ligne (probleme N+1).
     */
    @Query("select s from Sondage s join fetch s.signalement order by s.dateOuverture desc")
    List<Sondage> findAllAvecSignalement();

    /** Le sondage d'un signalement donne. Il n'y en a qu'un au plus. */
    @Query("select s from Sondage s join fetch s.signalement sig where sig.trackingId = ?1")
    Optional<Sondage> findBySignalementTrackingId(UUID signalementTrackingId);

    /** Un signalement ne peut porter qu'un seul sondage. */
    boolean existsBySignalementTrackingId(UUID signalementTrackingId);
}
