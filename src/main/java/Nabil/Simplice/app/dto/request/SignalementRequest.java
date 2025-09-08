package Nabil.Simplice.app.dto.request;

import Nabil.Simplice.app.enums.TypeService;
import java.util.List;
import java.util.UUID;

public class SignalementRequest {
    private String titre;
    private String code;
    private String description;
    private TypeService typeService;
    private Long serviceId;
    private List<UUID> fichiers;
    private List<String> fichiersPaths;
    private String commentaireService;
    private Integer priorite;
    private String latitude;
    private String longitude;
    private UUID ouvrierUuid;
    private UUID traiteurUuid;

    // Getters et Setters
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public List<UUID> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<UUID> fichiers) {
        this.fichiers = fichiers;
    }

    public String getCommentaireService() {
        return commentaireService;
    }

    public void setCommentaireService(String commentaireService) {
        this.commentaireService = commentaireService;
    }

    public UUID getTraiteurUuid() {
        return traiteurUuid;
    }

    public void setTraiteurUuid(UUID traiteurUuid) {
        this.traiteurUuid = traiteurUuid;
    }

    public UUID getOuvrierUuid() {
        return ouvrierUuid;
    }

    public void setOuvrierUuid(UUID ouvrierUuid) {
        this.ouvrierUuid = ouvrierUuid;
    }

    public List<String> getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(List<String> fichiersPaths) {
        this.fichiersPaths = fichiersPaths;
    }
}
