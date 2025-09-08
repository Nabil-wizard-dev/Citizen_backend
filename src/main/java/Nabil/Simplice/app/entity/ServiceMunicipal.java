package Nabil.Simplice.app.entity;

import java.time.LocalDate;

import Nabil.Simplice.app.enums.UtilisateurRole;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SERVICE_MUNICIPAL")
public class ServiceMunicipal extends ServiceEtat {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "type_service_municipal")
    private String typeServiceMunicipal;

    @Column(name = "budget_annuel")
    private Double budgetAnnuel;

    @Column(name = "nombre_personnel")
    private Integer nombrePersonnel;

    @Column(name = "services_specifiques")
    private String servicesSpecifiques;

    private UtilisateurRole role;

    public ServiceMunicipal() {
        super();
        this.role = UtilisateurRole.SERVICE_MUNICIPAL;
    }

    public ServiceMunicipal(LocalDate createDate, LocalDate updateDate, String createBy, String modifiedBy) {
        super(createDate, updateDate, createBy, modifiedBy);
        this.role = UtilisateurRole.SERVICE_MUNICIPAL;
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
} 