package Nabil.Simplice.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import Nabil.Simplice.app.entity.Ouvrier;

public interface OuvrierRepository extends JpaRepository<Ouvrier, Long> {
    Optional<Ouvrier> findByTrackingId(UUID trackingId);
    Optional<Ouvrier> findByEmail(String email);

    boolean existsByTrackingId(UUID trackingId);
}
