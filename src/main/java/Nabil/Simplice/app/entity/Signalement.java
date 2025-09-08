package Nabil.Simplice.app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.enums.StatutSignalement;
import Nabil.Simplice.app.enums.TypeService;
import Nabil.Simplice.app.utils.AuditTable;
import jakarta.persistence.*;
import org.springframework.data.domain.Auditable;


@Entity
@Table(name = "signalements")
public class Signalement extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    protected UUID trackingId;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, length = 1000)
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private StatutSignalement statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_service", nullable = false)
    private TypeService typeService; // Type de service concerné (Hygiène ou Municipal)

    @Column(name = "service_id", nullable = false)
    private Long serviceId; // Référence au service spécifique

    @OneToMany(mappedBy = "signalement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FichierJoin> fichiers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "fichiers_paths", joinColumns = @JoinColumn(name = "signalement_id"))
    @Column(name = "fichier_path")
    private List<String> fichiersPaths = new ArrayList<>();

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "tache_id")
//    private Tache tache;

    @Column(name = "commentaire_service")
    private String commentaireService;

    @Column(name = "priorite")
    private Integer priorite;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "ouvrier_id")
    private Ouvrier ouvrier;

    @ManyToOne
    @JoinColumn(name = "Traiteur_id")
    private AutoriteLocale utilisateurTraiteur;

    // constructeurs
    public Signalement() {

    }

    public Signalement(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
        super(createDate, updateDate, createBy, modifiedBy);
    }

    //    @PrePersist
//    protected void onCreate() {
//        dateCreation = LocalDateTime.now();
//        dateDerniereModification = dateCreation;
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        dateDerniereModification = LocalDateTime.now();
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public StatutSignalement getStatut() {
        return statut;
    }

    public void setStatut(StatutSignalement statut) {
        this.statut = statut;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<FichierJoin> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<FichierJoin> fichiers) {
        this.fichiers = fichiers;
    }

    public String getCommentaireService() {
        return commentaireService;
    }

    public void setCommentaireService(String commentaireService) {
        this.commentaireService = commentaireService;
    }

    public Integer getPriorite() {
        return priorite;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public AutoriteLocale getUtilisateurTraiteur() {
        return utilisateurTraiteur;
    }

    public void setUtilisateurTraiteur(AutoriteLocale utilisateurTraiteur) {
        this.utilisateurTraiteur = utilisateurTraiteur;
    }

    public Ouvrier getOuvrier() {
        return ouvrier;
    }

    public void setOuvrier(Ouvrier ouvrier) {
        this.ouvrier = ouvrier;
    }

    public List<String> getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(List<String> fichiersPaths) {
        this.fichiersPaths = fichiersPaths;
    }

//    public Tache getTache() {
//        return tache;
//    }
//
//    public void setTache(Tache tache) {
//        this.tache = tache;
//    }
}
    