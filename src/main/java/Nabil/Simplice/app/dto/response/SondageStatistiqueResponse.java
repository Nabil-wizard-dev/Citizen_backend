package Nabil.Simplice.app.dto.response;

import java.util.UUID;

/**
 * Resultat agrege d'un sondage.
 * Evite au client de telecharger toutes les reponses pour afficher une moyenne.
 */
public class SondageStatistiqueResponse {

    private UUID sondageId;

    /** Moyenne des notes, arrondie a une decimale. 0 si aucune reponse. */
    private double moyenneNote;

    /** Nombre total de reponses recues. */
    private long totalReponses;

    /** Nombre de reponses ou l'utilisateur a confirme. */
    private long nombreConfirmations;

    /** Part de confirmations sur le total, en pourcentage. 0 si aucune reponse. */
    private double tauxConfirmation;

    public SondageStatistiqueResponse() {
    }

    public SondageStatistiqueResponse(UUID sondageId, double moyenneNote, long totalReponses, long nombreConfirmations,
                                      double tauxConfirmation) {
        this.sondageId = sondageId;
        this.moyenneNote = moyenneNote;
        this.totalReponses = totalReponses;
        this.nombreConfirmations = nombreConfirmations;
        this.tauxConfirmation = tauxConfirmation;
    }

    // getters et setters

    public UUID getSondageId() {
        return sondageId;
    }

    public void setSondageId(UUID sondageId) {
        this.sondageId = sondageId;
    }

    public double getMoyenneNote() {
        return moyenneNote;
    }

    public void setMoyenneNote(double moyenneNote) {
        this.moyenneNote = moyenneNote;
    }

    public long getTotalReponses() {
        return totalReponses;
    }

    public void setTotalReponses(long totalReponses) {
        this.totalReponses = totalReponses;
    }

    public long getNombreConfirmations() {
        return nombreConfirmations;
    }

    public void setNombreConfirmations(long nombreConfirmations) {
        this.nombreConfirmations = nombreConfirmations;
    }

    public double getTauxConfirmation() {
        return tauxConfirmation;
    }

    public void setTauxConfirmation(double tauxConfirmation) {
        this.tauxConfirmation = tauxConfirmation;
    }
}
