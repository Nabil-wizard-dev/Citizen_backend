package Nabil.Simplice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nabil.Simplice.app.entity.Geolocalisation;

@Repository
public interface GeolocalisationRepository extends JpaRepository<Geolocalisation, Long> {
}
