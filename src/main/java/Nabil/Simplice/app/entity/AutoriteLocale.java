package Nabil.Simplice.app.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Nabil.Simplice.app.enums.UtilisateurRole;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("autorites")
public class AutoriteLocale extends Utilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "utilisateurTraiteur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Signalement> signalements = new ArrayList<>();

	public AutoriteLocale() {
		super();
		this.role = UtilisateurRole.AUTORITE_LOCALE;
		// TODO Auto-generated constructor stub
	}

	public AutoriteLocale(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
		super(createDate, updateDate, createBy, modifiedBy);
		this.role = UtilisateurRole.AUTORITE_LOCALE;
		// TODO Auto-generated constructor stub
	}
	

}
