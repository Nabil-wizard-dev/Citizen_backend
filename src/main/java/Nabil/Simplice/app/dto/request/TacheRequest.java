package Nabil.Simplice.app.dto.request;

import jakarta.persistence.Column;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TacheRequest {

    private Date dateDebut;

    private Date dateFin;

    private MultipartFile fichierDevis;

    private boolean isActiver;

    private boolean isResolu;

    private UUID signalementId;

    private List<UUID> EtatDeTacheIds;

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

    public MultipartFile getFichierDevis() {
        return fichierDevis;
    }

    public void setFichierDevis(MultipartFile fichierDevis) {
        this.fichierDevis = fichierDevis;
    }
}
