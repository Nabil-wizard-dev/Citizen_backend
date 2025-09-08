package Nabil.Simplice.app.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.enums.UtilisateurRole;
import Nabil.Simplice.app.enums.specilateOuvriere;
import jakarta.persistence.*;


@Entity
@DiscriminatorValue("ouvriers")
public class Ouvrier extends Utilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "ouvrier", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Signalement> signalements = new ArrayList<>();

	@Column()
	private UUID signalementActuelId;

	@Enumerated(EnumType.STRING)
	private specilateOuvriere specialite;

	@Column(name = "service_id")
	private UUID serviceId; // Référence au service spécifique




	//ctors
	public Ouvrier() {
		super();
		this.role = UtilisateurRole.OUVRIER;
		// TODO Auto-generated constructor stub
	}

	public Ouvrier(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
		super(createDate, updateDate, createBy, modifiedBy);
		this.role = UtilisateurRole.OUVRIER;
		// TODO Auto-generated constructor stub
	}
	
	
	

	public UUID getSignalementActuelId() {
		return signalementActuelId;
	}

	public void setSignalementActuelId(UUID signalementActuelId) {
		this.signalementActuelId = signalementActuelId;
	}

	public specilateOuvriere getSpecialite() {
		return specialite;
	}

	public void setSpecialite(specilateOuvriere specialite) {
		this.specialite = specialite;
	}

	public List<Signalement> getSignalements() {
		return signalements;
	}

	public void setSignalements(List<Signalement> signalements) {
		this.signalements = signalements;
	}

	public UUID getServiceId() {
		return serviceId;
	}

	public void setServiceId(UUID serviceId) {
		this.serviceId = serviceId;
	}
}
