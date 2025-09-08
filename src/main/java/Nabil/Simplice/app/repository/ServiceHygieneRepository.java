package Nabil.Simplice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nabil.Simplice.app.entity.ServiceHygiene;

@Repository
public interface ServiceHygieneRepository extends JpaRepository<ServiceHygiene, Long> {
    // Méthodes personnalisées si nécessaire
} 