package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.SondageFeedbackRequest;
import Nabil.Simplice.app.dto.response.SondageFeedbackResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SondageFeedbackService {

    ResponseEntity<ApiResponse<List<SondageFeedbackResponse>>> getAllFeedbacks();

    ResponseEntity<ApiResponse<List<SondageFeedbackResponse>>> getFeedbacksBySondage(UUID sondageId);

    ResponseEntity<ApiResponse<SondageFeedbackResponse>> getFeedbackById(UUID id);

    // La réponse de l'utilisateur connecté à un sondage : data à null s'il n'a pas encore répondu
    ResponseEntity<ApiResponse<SondageFeedbackResponse>> getMaReponse(UUID sondageId);

    ResponseEntity<ApiResponse<SondageFeedbackResponse>> addFeedback(SondageFeedbackRequest request);

    ResponseEntity<ApiResponse<SondageFeedbackResponse>> updateFeedback(UUID id, SondageFeedbackRequest request);

    ResponseEntity<ApiResponse<Void>> deleteFeedback(UUID id);
}
