package Nabil.Simplice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nabil.Simplice.app.entity.ServiceMunicipal;

@Repository
public interface ServiceMunicipalRepository extends JpaRepository<ServiceMunicipal, Long> {
    // Méthodes personnalisées si nécessaire
} 