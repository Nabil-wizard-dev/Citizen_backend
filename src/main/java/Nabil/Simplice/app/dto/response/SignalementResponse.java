package Nabil.Simplice.app.dto.response;

import java.util.UUID;
import Nabil.Simplice.app.enums.UtilisateurRole;
import java.io.ObjectInputFilter.Status;
import Nabil.Simplice.app.enums.TypeService;
import Nabil.Simplice.app.enums.StatutSignalement;
import java.time.LocalDateTime;
import java.util.List;

public class SignalementResponse {

    // Identifiant de suivi
    private UUID trackingId;
    // Titre
    private String titre;
    // Code du signalement
    private String code;
    // Description
    private String description;
    // Statut
    private StatutSignalement statut;
    // Type de service
    private TypeService typeService;
    // Identifiant du service
    private Long serviceId;
    // Utilisateur créateur
    private UUID UtilisateurCreateur;
    // Liste des fichiers
    private List<UUID> fichiers;

    private List<String> fichiersPaths;

    // Commentaire du service
    private String commentaireService;
    // Priorité
    private Integer priorite;
    private String latitude;
    private String longitude;
    private UUID ouvrierUuid;
    private UUID traiteurUuid;

// Getters et setters

    public UUID getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
    }

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

    public UUID getUtilisateurCreateur() {
        return UtilisateurCreateur;
    }

    public void setUtilisateurCreateur(UUID utilisateurCreateur) {
        UtilisateurCreateur = utilisateurCreateur;
    }

    public List<UUID> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<UUID> fichiers) {
        this.fichiers = fichiers;
    }

    public UUID getOuvrierUuid() {
        return ouvrierUuid;
    }

    public void setOuvrierUuid(UUID ouvrierUuid) {
        this.ouvrierUuid = ouvrierUuid;
    }

    public UUID getTraiteurUuid() {
        return traiteurUuid;
    }

    public void setTraiteurUuid(UUID traiteurUuid) {
        this.traiteurUuid = traiteurUuid;
    }

    public List<String> getFichiersPaths() {
        return fichiersPaths;
    }

    public void setFichiersPaths(List<String> fichiersPaths) {
        this.fichiersPaths = fichiersPaths;
    }
}
