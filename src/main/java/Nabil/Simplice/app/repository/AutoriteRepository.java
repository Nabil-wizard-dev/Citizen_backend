package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.AutoriteLocale;
import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutoriteRepository extends JpaRepository<AutoriteLocale, Long> {

    Optional<AutoriteLocale> findByTrackingId(UUID trackingId);
}
