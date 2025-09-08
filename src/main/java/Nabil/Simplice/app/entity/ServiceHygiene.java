package Nabil.Simplice.app.entity;

import java.time.LocalDate;

import Nabil.Simplice.app.enums.UtilisateurRole;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SERVICE_HYGIENE")
public class ServiceHygiene extends ServiceEtat {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "type_inspection")
    private String typeInspection;

    @Column(name = "frequence_controle")
    private String frequenceControle;

    @Column(name = "equipements_disponibles")
    private String equipementsDisponibles;

    @Column(name = "certifications")
    private String certifications;

    private UtilisateurRole role;

    public ServiceHygiene() {
        super();
        this.role = UtilisateurRole.SERVICE_HYGIENE;
    }

    public ServiceHygiene(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
        super(createDate, updateDate, createBy, modifiedBy);
        this.role = UtilisateurRole.SERVICE_HYGIENE;
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
} 