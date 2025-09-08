package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    @Override
    Optional<Annonce> findById(Long aLong);

    Optional<Annonce> findByTrackingId(UUID trackingId);

    boolean existsByTrackingId(UUID trackingId);

    @Query("select a from Annonce a where a.entiteId = ?1 ")
    List<Annonce> findAllByEntiteId(UUID entiteId);


}
