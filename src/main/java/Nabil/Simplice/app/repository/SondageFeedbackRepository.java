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
     * Le "join fetch" charge le citoyen et le sondage dans la meme requete : sans lui,
     * afficher 50 reponses declencherait 50 requetes supplementaires (probleme N+1).
     */
    @Query("select f from SondageFeedback f "
         + "join fetch f.citoyen "
         + "join fetch f.sondage s "
         + "where s.trackingId = ?1 "
         + "order by f.dateReponse desc")
    List<SondageFeedback> findBySondageTrackingId(UUID sondageTrackingId);

    /** La reponse d'un citoyen donne a un sondage donne : permet au front de savoir s'il a deja repondu. */
    @Query("select f from SondageFeedback f "
         + "join fetch f.citoyen c "
         + "join fetch f.sondage s "
         + "where s.trackingId = ?1 and c.trackingId = ?2")
    Optional<SondageFeedback> findBySondageEtCitoyen(UUID sondageTrackingId, UUID citoyenTrackingId);

    /** Un citoyen ne repond qu'une seule fois a un sondage donne. */
    boolean existsBySondageTrackingIdAndCitoyenTrackingId(UUID sondageTrackingId, UUID citoyenTrackingId);

    /** Supprime toutes les reponses d'un sondage. Appelee lors de la suppression du sondage. */
    void deleteBySondageTrackingId(UUID sondageTrackingId);
}
