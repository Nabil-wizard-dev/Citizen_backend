package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SondageService {

    ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondages();

    ResponseEntity<ApiResponse<SondageResponse>> getSondageById(UUID id);

    // Le sondage d'un signalement donné (il n'y en a qu'un au plus)
    ResponseEntity<ApiResponse<SondageResponse>> getSondageBySignalement(UUID signalementId);

    ResponseEntity<ApiResponse<SondageResponse>> addSondage(SondageRequest request);

    ResponseEntity<ApiResponse<SondageResponse>> updateSondage(UUID id, SondageRequest request);

    // Clôturer le sondage : fige le résultat et la moyenne finale
    ResponseEntity<ApiResponse<SondageResponse>> cloturerSondage(UUID id);

    ResponseEntity<ApiResponse<Void>> deleteSondage(UUID id);
}
