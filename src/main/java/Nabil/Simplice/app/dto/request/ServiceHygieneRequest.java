package Nabil.Simplice.app.dto.request;

import java.time.LocalTime;

public class ServiceHygieneRequest {
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
    private String typeInspection;
    private String frequenceControle;
    private String equipementsDisponibles;
    private String certifications;
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

    public String getTypeInspection() {
        return typeInspection;
    }

    public void setTypeInspection(String typeInspection) {
        this.typeInspection = typeInspection;
    }

    public String getFrequenceControle() {
        return frequenceControle;
    }

    public void setFrequenceControle(String frequenceControle) {
        this.frequenceControle = frequenceControle;
    }

    public String getEquipementsDisponibles() {
        return equipementsDisponibles;
    }

    public void setEquipementsDisponibles(String equipementsDisponibles) {
        this.equipementsDisponibles = equipementsDisponibles;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }
} 