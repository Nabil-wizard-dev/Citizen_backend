package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.SondageFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SondageFeedbackRepository extends JpaRepository<SondageFeedback, Long> {

    Optional<SondageFeedback> findByTrackingId(UUID trackingId);

    boolean existsByTrackingId(UUID trackingId);

    /**
     * Toutes les reponses d'un sondage, la plus recente en premier.
     * Le "join fetch" charge l'auteur et le sondage dans la meme requete : sans lui,
     * afficher 50 reponses declencherait 50 requetes supplementaires (probleme N+1).
     */
    @Query("select f from SondageFeedback f "
         + "join fetch f.utilisateur "
         + "join fetch f.sondage s "
         + "where s.trackingId = ?1 "
         + "order by f.dateReponse desc")
    List<SondageFeedback> findBySondageTrackingId(UUID sondageTrackingId);

    /** Un utilisateur ne repond qu'une seule fois a un sondage donne. */
    boolean existsBySondageTrackingIdAndUtilisateurTrackingId(UUID sondageTrackingId, UUID utilisateurTrackingId);

    // ---- agregats : calcules par la base, aucune ligne n'est transferee ----

    /** Nombre total de reponses recues par un sondage. */
    long countBySondageTrackingId(UUID sondageTrackingId);

    /** Nombre de reponses dans lesquelles l'utilisateur a confirme. */
    long countBySondageTrackingIdAndConfirmationTrue(UUID sondageTrackingId);

    /** Moyenne des notes. Renvoie null si le sondage n'a encore aucune reponse. */
    @Query("select avg(f.note) from SondageFeedback f where f.sondage.trackingId = ?1")
    Double moyenneNoteBySondage(UUID sondageTrackingId);
}
