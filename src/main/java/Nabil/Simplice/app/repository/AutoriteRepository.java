package Nabil.Simplice.app.repository;

import Nabil.Simplice.app.entity.AutoriteLocale;
import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AutoriteRepository extends JpaRepository<AutoriteLocale, Long> {

    Optional<AutoriteLocale> findByTrackingId(UUID trackingId);
}
