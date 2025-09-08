package Nabil.Simplice.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nabil.Simplice.app.entity.FichierJoin;
import Nabil.Simplice.app.entity.Utilisateur;
import Nabil.Simplice.app.enums.ExtensionFichier;

@Repository
public interface FichierJoinRepository extends JpaRepository<FichierJoin, Long> {
	
	//findAll

	List<FichierJoin> findByExtension(ExtensionFichier extension);// Trouver les fichiers par extension
	List<FichierJoin> findBySignalementId(Long signalementId); // Trouver les fichiers par signalement

	//find one
	Optional<FichierJoin> findByTrackingId(UUID trackingId);
	Optional<FichierJoin> findByChemin(String chemin);



	//exists
	boolean existsByTrackingId(UUID trackingId);
	boolean existsByChemin(String chemin);



	

	
//	// Trouver les fichiers par nom contenant
//	List<FichierJoin> findByNomContainingIgnoreCase(String nom);
	


}
