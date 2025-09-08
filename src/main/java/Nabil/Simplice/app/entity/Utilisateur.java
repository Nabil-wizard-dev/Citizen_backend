package Nabil.Simplice.app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import Nabil.Simplice.app.enums.UtilisateurRole;
import Nabil.Simplice.app.utils.AuditTable;

@Entity @Table(name="Utilisateurs") @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Utilisateur extends AuditTable implements Serializable,UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@jakarta.persistence.Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	protected Long Id;
	@Column(nullable = false)
	protected UUID trackingId;
	@Column(nullable = false)
	protected String nom;
	@Column(nullable = false)
	protected String prenom;
	@Column(nullable = false,unique = true)
	protected String cni;
	@Column(unique = true,nullable = false)
	protected String email;
	@Column(unique = true,nullable = false)
	protected int numero;
	@Column(unique = false,nullable = true)
	protected String dateNaissance;
	@Column(unique = false,nullable = true)
	protected String adresse;
	@Column(nullable = false)
	protected String MotDePasse;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected UtilisateurRole role;
	
	@Column(name = "photo_profil", nullable = true)
	protected String photoProfil;
	
	//constructeurs
	public Utilisateur() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Utilisateur(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
		super(createDate, updateDate, createBy, modifiedBy);
		// TODO Auto-generated constructor stub
	}
	
	//getters et setters
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public UUID getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(UUID trackingId) {
		this.trackingId = trackingId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getMotDePasse() {
		return MotDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		MotDePasse = motDePasse;
	}
	public String getCni() {
		return cni;
	}
	public void setCni(String cni) {
		this.cni = cni;
	}
	public UtilisateurRole getRole() {
		return role;
	}
	public void setRole(UtilisateurRole role) {
		this.role = role;
	}
	public String getPhotoProfil() {
		return photoProfil;
	}
	public void setPhotoProfil(String photoProfil) {
		this.photoProfil = photoProfil;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	// methode de UserDetails
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name())); 
	    return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return MotDePasse;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
		
}
