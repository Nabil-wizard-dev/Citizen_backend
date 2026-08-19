package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SondageService {

    ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondages();
    ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondagesByEntityId(UUID entityId);
    ResponseEntity<ApiResponse<SondageResponse>> getSondageById(UUID id);
    ResponseEntity<ApiResponse<SondageResponse>> addSondage(SondageRequest a);
    ResponseEntity<ApiResponse<SondageResponse>> updateSondage(UUID id, SondageRequest a);
    ResponseEntity<ApiResponse<Void>> deleteSondage(UUID id);
    
}
