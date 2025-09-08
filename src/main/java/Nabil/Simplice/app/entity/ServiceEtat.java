package Nabil.Simplice.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import Nabil.Simplice.app.enums.TypeService;

@Entity
@Table(name = "services_etat")
public class ServiceEtat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "zone_couverture")
    private String zoneCouverture;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "heure_ouverture", nullable = false)
    private LocalTime heureOuverture;

    @Column(name = "heure_fermeture", nullable = false)
    private LocalTime heureFermeture;

    @Column(name = "jours_ouverture", nullable = false)
    private String joursOuverture;

    @Column(name = "responsable_nom")
    private String responsableNom;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_service", nullable = false)
    private TypeService typeService;

    public ServiceEtat() {
    }

    public ServiceEtat(LocalDate dateDebut, LocalDate dateFin, String zoneCouverture, String description) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.zoneCouverture = zoneCouverture;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getZoneCouverture() {
        return zoneCouverture;
    }

    public void setZoneCouverture(String zoneCouverture) {
        this.zoneCouverture = zoneCouverture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalTime getHeureOuverture() {
        return heureOuverture;
    }

    public void setHeureOuverture(LocalTime heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    public LocalTime getHeureFermeture() {
        return heureFermeture;
    }

    public void setHeureFermeture(LocalTime heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public String getJoursOuverture() {
        return joursOuverture;
    }

    public void setJoursOuverture(String joursOuverture) {
        this.joursOuverture = joursOuverture;
    }

    public String getResponsableNom() {
        return responsableNom;
    }

    public void setResponsableNom(String responsableNom) {
        this.responsableNom = responsableNom;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public static ServiceEtatBuilder builder() {
        return new ServiceEtatBuilder();
    }

    public static class ServiceEtatBuilder {
        private Long id;
        private LocalDate dateDebut;
        private LocalDate dateFin;
        private String zoneCouverture;
        private String description;
        private String createdBy;
        private LocalDate createDate;
        private String nom;
        private String adresse;
        private String telephone;
        private String email;
        private LocalTime heureOuverture;
        private LocalTime heureFermeture;
        private String joursOuverture;
        private String responsableNom;
        private TypeService typeService;

        public ServiceEtatBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ServiceEtatBuilder dateDebut(LocalDate dateDebut) {
            this.dateDebut = dateDebut;
            return this;
        }

        public ServiceEtatBuilder dateFin(LocalDate dateFin) {
            this.dateFin = dateFin;
            return this;
        }

        public ServiceEtatBuilder zoneCouverture(String zoneCouverture) {
            this.zoneCouverture = zoneCouverture;
            return this;
        }

        public ServiceEtatBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ServiceEtatBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ServiceEtatBuilder createDate(LocalDate createDate) {
            this.createDate = createDate;
            return this;
        }

        public ServiceEtatBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public ServiceEtatBuilder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        public ServiceEtatBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public ServiceEtatBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ServiceEtatBuilder heureOuverture(LocalTime heureOuverture) {
            this.heureOuverture = heureOuverture;
            return this;
        }

        public ServiceEtatBuilder heureFermeture(LocalTime heureFermeture) {
            this.heureFermeture = heureFermeture;
            return this;
        }

        public ServiceEtatBuilder joursOuverture(String joursOuverture) {
            this.joursOuverture = joursOuverture;
            return this;
        }

        public ServiceEtatBuilder responsableNom(String responsableNom) {
            this.responsableNom = responsableNom;
            return this;
        }

        public ServiceEtatBuilder typeService(TypeService typeService) {
            this.typeService = typeService;
            return this;
        }

        public ServiceEtat build() {
            ServiceEtat serviceEtat = new ServiceEtat();
            serviceEtat.setId(this.id);
            serviceEtat.setDateDebut(this.dateDebut);
            serviceEtat.setDateFin(this.dateFin);
            serviceEtat.setZoneCouverture(this.zoneCouverture);
            serviceEtat.setDescription(this.description);
            serviceEtat.setCreatedBy(this.createdBy);
            serviceEtat.setCreateDate(this.createDate);
            serviceEtat.setNom(this.nom);
            serviceEtat.setAdresse(this.adresse);
            serviceEtat.setTelephone(this.telephone);
            serviceEtat.setEmail(this.email);
            serviceEtat.setHeureOuverture(this.heureOuverture);
            serviceEtat.setHeureFermeture(this.heureFermeture);
            serviceEtat.setJoursOuverture(this.joursOuverture);
            serviceEtat.setResponsableNom(this.responsableNom);
            serviceEtat.setTypeService(this.typeService);
            return serviceEtat;
        }
    }
} 