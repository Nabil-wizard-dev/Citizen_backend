package Nabil.Simplice.app.mappers;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.entity.Sondage;
import Nabil.Simplice.app.enums.StatutSondage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SondageMappers {

    /** Le signalement n'est pas construit depuis la requete : il est resolu par le service. */
    public Sondage toEntity(SondageRequest request, Signalement signalement) {
        Sondage sondage = new Sondage();
        sondage.setTrackingId(UUID.randomUUID());
        sondage.setTitre(request.getTitre());
        sondage.setDescription(request.getDescription());
        sondage.setSignalement(signalement);
        sondage.setStatut(StatutSondage.OUVERT);
        sondage.setDateOuverture(LocalDateTime.now());
        return sondage;
    }

    public SondageResponse toResponse(Sondage sondage) {
        SondageResponse response = new SondageResponse();
        response.setTrackingId(sondage.getTrackingId());
        response.setTitre(sondage.getTitre());
        response.setDescription(sondage.getDescription());
        response.setStatut(sondage.getStatut());
        response.setDateOuverture(sondage.getDateOuverture());
        response.setDateCloture(sondage.getDateCloture());
        response.setNbOui(sondage.getNbOui());
        response.setNbNon(sondage.getNbNon());
        response.setNbNotes(sondage.getNbNotes());
        response.setSommeNotes(sondage.getSommeNotes());
        response.setResultat(sondage.getResultat());
        response.setMoyenneFinale(sondage.getMoyenneFinale());

        if (sondage.getSignalement() != null) {
            response.setSignalementId(sondage.getSignalement().getTrackingId());
        }

        // valeurs derivees : le front les affiche telles quelles
        response.setTotalReponses(sondage.getNbOui() + sondage.getNbNon());
        response.setMoyenneCourante(moyenne(sondage.getSommeNotes(), sondage.getNbNotes()));

        return response;
    }

    /** Moyenne arrondie a une decimale, null tant qu'aucune note n'a ete deposee. */
    public Double moyenne(int sommeNotes, int nbNotes) {
        if (nbNotes <= 0) {
            return null;
        }
        return Math.round((sommeNotes * 10d) / nbNotes) / 10d;
    }
}
