package Nabil.Simplice.app.dto.request;

import Nabil.Simplice.app.enums.TypeService;
import java.util.List;
import java.util.UUID;

public class SignalementRequest {
    private String titre;
    // Note: le code n'est plus fourni par le client. Il est attribué par le serveur
    // au moment de la création, en fonction de la position (regroupement par dossier).
    private String description;
    private TypeService typeService;
    private Long serviceId;
    // Note: Les fichiers sont maintenant gérés directement via MultipartFile dans les contrôleurs
    // private List<UUID> fichiers;
    private String commentaireService;
    private Integer priorite;
    private Double latitude;
    private Double longitude;
    private UUID ouvrierUuid;
    private UUID traiteurUuid;

    // Getters et Setters
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    // Méthodes getFichiers et setFichiers supprimées car les fichiers sont gérés via MultipartFile

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

}
