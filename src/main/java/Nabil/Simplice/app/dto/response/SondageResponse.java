package Nabil.Simplice.app.dto.response;

import Nabil.Simplice.app.enums.StatutSondage;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Un sondage et ses resultats en une seule reponse : le front n'a jamais besoin
 * de telecharger les reponses individuelles pour afficher un compteur ou une moyenne.
 */
public class SondageResponse {

    private UUID trackingId;

    private String titre;

    private String description;

    private UUID signalementId;

    private StatutSondage statut;

    private LocalDateTime dateOuverture;

    private LocalDateTime dateCloture;

    // compteurs bruts
    private int nbOui;

    private int nbNon;

    private int nbNotes;

    private int sommeNotes;

    // resultats figes a la cloture (null tant que le sondage est ouvert)
    private Boolean resultat;

    private Double moyenneFinale;

    // valeurs derivees, calculees par le mapper pour eviter au front de le faire
    private int totalReponses;

    private Double moyenneCourante;

    // getters et setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getSignalementId() {
        return signalementId;
    }

    public void setSignalementId(UUID signalementId) {
        this.signalementId = signalementId;
    }

    public StatutSondage getStatut() {
        return statut;
    }

    public void setStatut(StatutSondage statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDateTime dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDateTime getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public int getNbOui() {
        return nbOui;
    }

    public void setNbOui(int nbOui) {
        this.nbOui = nbOui;
    }

    public int getNbNon() {
        return nbNon;
    }

    public void setNbNon(int nbNon) {
        this.nbNon = nbNon;
    }

    public int getNbNotes() {
        return nbNotes;
    }

    public void setNbNotes(int nbNotes) {
        this.nbNotes = nbNotes;
    }

    public int getSommeNotes() {
        return sommeNotes;
    }

    public void setSommeNotes(int sommeNotes) {
        this.sommeNotes = sommeNotes;
    }

    public Boolean getResultat() {
        return resultat;
    }

    public void setResultat(Boolean resultat) {
        this.resultat = resultat;
    }

    public Double getMoyenneFinale() {
        return moyenneFinale;
    }

    public void setMoyenneFinale(Double moyenneFinale) {
        this.moyenneFinale = moyenneFinale;
    }

    public int getTotalReponses() {
        return totalReponses;
    }

    public void setTotalReponses(int totalReponses) {
        this.totalReponses = totalReponses;
    }

    public Double getMoyenneCourante() {
        return moyenneCourante;
    }

    public void setMoyenneCourante(Double moyenneCourante) {
        this.moyenneCourante = moyenneCourante;
    }
}
