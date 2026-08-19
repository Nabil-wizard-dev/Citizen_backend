package Nabil.Simplice.app.controller;

import Nabil.Simplice.app.dto.request.SondageFeedbackRequest;
import Nabil.Simplice.app.dto.response.SondageFeedbackResponse;
import Nabil.Simplice.app.dto.response.SondageStatistiqueResponse;
import Nabil.Simplice.app.service.SondageFeedbackService;
import Nabil.Simplice.app.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedbacks")
public class SondageFeedbackController {

    private final SondageFeedbackService sondageFeedbackService;

    public SondageFeedbackController(SondageFeedbackService sondageFeedbackService) {
        this.sondageFeedbackService = sondageFeedbackService;
    }

    @GetMapping("getAll")
    public ResponseEntity<ApiResponse<List<SondageFeedbackResponse>>> getAllFeedbacks() {
        return sondageFeedbackService.getAllFeedbacks();
    }

    @GetMapping("getAllBySondage/{sondageId}")
    public ResponseEntity<ApiResponse<List<SondageFeedbackResponse>>> getFeedbacksBySondage(@PathVariable UUID sondageId) {
        return sondageFeedbackService.getFeedbacksBySondage(sondageId);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> getFeedbackById(@PathVariable UUID id) {
        return sondageFeedbackService.getFeedbackById(id);
    }

    @GetMapping("statistiques/{sondageId}")
    public ResponseEntity<ApiResponse<SondageStatistiqueResponse>> getStatistiques(@PathVariable UUID sondageId) {
        return sondageFeedbackService.getStatistiques(sondageId);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> addFeedback(@Valid @RequestBody SondageFeedbackRequest request) {
        return sondageFeedbackService.addFeedback(request);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<SondageFeedbackResponse>> updateFeedback(@PathVariable UUID id,
                                                                               @Valid @RequestBody SondageFeedbackRequest request) {
        return sondageFeedbackService.updateFeedback(id, request);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFeedback(@PathVariable UUID id) {
        return sondageFeedbackService.deleteFeedback(id);
    }
}
