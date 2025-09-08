package Nabil.Simplice.app.dto.response;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TacheResponse {
    private UUID TrackingId;

    private Date dateDebut;

    private Date dateFin;

    private String  fichierDevis;

    private boolean isActiver;

    private boolean isResolu;

    private UUID signalementId;

    private List<UUID> EtatDeTacheIds;

    public UUID getTrackingId() {
        return TrackingId;
    }

    public void setTrackingId(UUID trackingId) {
        TrackingId = trackingId;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isActiver() {
        return isActiver;
    }

    public void setActiver(boolean activer) {
        isActiver = activer;
    }

    public boolean isResolu() {
        return isResolu;
    }

    public void setResolu(boolean resolu) {
        isResolu = resolu;
    }

    public UUID getSignalementId() {
        return signalementId;
    }

    public void setSignalementId(UUID signalementId) {
        this.signalementId = signalementId;
    }

    public List<UUID> getEtatDeTacheIds() {
        return EtatDeTacheIds;
    }

    public void setEtatDeTacheIds(List<UUID> etatDeTacheIds) {
        EtatDeTacheIds = etatDeTacheIds;
    }

    public String getFichierDevis() {
        return fichierDevis;
    }

    public void setFichierDevis(String fichierDevis) {
        this.fichierDevis = fichierDevis;
    }
}
