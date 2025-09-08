package Nabil.Simplice.app.entity;

import java.time.LocalDate;

import Nabil.Simplice.app.enums.UtilisateurRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("admins")
public class Administrateur extends Utilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Administrateur() {
		super();
		this.role = UtilisateurRole.ADMINISTRATEUR;
		// TODO Auto-generated constructor stub
	}

	public Administrateur(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
		super(createDate, updateDate, createBy, modifiedBy);
		this.role = UtilisateurRole.ADMINISTRATEUR;
		// TODO Auto-generated constructor stub
	}
	

}
