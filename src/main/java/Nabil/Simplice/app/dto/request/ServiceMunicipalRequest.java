package Nabil.Simplice.app.dto.request;

import java.time.LocalTime;

public class ServiceMunicipalRequest {
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private LocalTime heureOuverture;
    private LocalTime heureFermeture;
    private String description;
    private String joursOuverture;
    private String responsableNom;
    private String zoneCouverture;
    private String typeServiceMunicipal;
    private Double budgetAnnuel;
    private Integer nombrePersonnel;
    private String servicesSpecifiques;
    private String typeService;

    // Getters et Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getZoneCouverture() {
        return zoneCouverture;
    }

    public void setZoneCouverture(String zoneCouverture) {
        this.zoneCouverture = zoneCouverture;
    }

    public String getTypeServiceMunicipal() {
        return typeServiceMunicipal;
    }

    public void setTypeServiceMunicipal(String typeServiceMunicipal) {
        this.typeServiceMunicipal = typeServiceMunicipal;
    }

    public Double getBudgetAnnuel() {
        return budgetAnnuel;
    }

    public void setBudgetAnnuel(Double budgetAnnuel) {
        this.budgetAnnuel = budgetAnnuel;
    }

    public Integer getNombrePersonnel() {
        return nombrePersonnel;
    }

    public void setNombrePersonnel(Integer nombrePersonnel) {
        this.nombrePersonnel = nombrePersonnel;
    }

    public String getServicesSpecifiques() {
        return servicesSpecifiques;
    }

    public void setServicesSpecifiques(String servicesSpecifiques) {
        this.servicesSpecifiques = servicesSpecifiques;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }
} 