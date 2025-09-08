package Nabil.Simplice.app.entity;

import java.time.LocalDate;

import Nabil.Simplice.app.enums.UtilisateurRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("citoyens")
public class Citoyen extends Utilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Citoyen() {
		super();
		this.role = UtilisateurRole.CITOYEN;
		// TODO Auto-generated constructor stub
	}

	public Citoyen(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
		super(createDate, updateDate, createBy, modifiedBy);
		this.role = UtilisateurRole.CITOYEN;
		// TODO Auto-generated constructor stub
	}
	

}
