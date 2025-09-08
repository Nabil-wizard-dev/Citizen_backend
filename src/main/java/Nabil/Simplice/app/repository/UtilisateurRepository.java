package Nabil.Simplice.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Nabil.Simplice.app.entity.Utilisateur;
import Nabil.Simplice.app.enums.UtilisateurRole;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	
	
	//find all
		@Query("select u from Utilisateur u where u.role = ?1")
		List<Utilisateur> findByRole(UtilisateurRole role);
		@Query("select u from Utilisateur u where u.role = ?1 order by u.nom")
		List<Utilisateur> findByRoleOrderByNom(UtilisateurRole role);
		@Query("select u from Utilisateur u where u.role = ?1order by u.email")
		List<Utilisateur> findByRoleOrderByEmail(UtilisateurRole role);
		
		
		//find one
		Optional<Utilisateur> findByEmail(String email);
		Optional<Utilisateur> findByNumero(int numero);
		Optional<Utilisateur> findByTrackingId(UUID trackingId);

		
		//exists
		boolean existsByEmail(String email);
		boolean existsByNumero(int numero);
		boolean existsByTrackingId(UUID trackingId);
}
