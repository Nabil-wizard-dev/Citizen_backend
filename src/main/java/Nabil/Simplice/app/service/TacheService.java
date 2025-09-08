package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.TacheRequest;
import Nabil.Simplice.app.dto.response.TacheResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TacheService {
   ResponseEntity<ApiResponse<TacheResponse>> creerTache(TacheRequest request);

    ResponseEntity<ApiResponse<List<TacheResponse>>> getAll();

    ResponseEntity<ApiResponse<TacheResponse>> getById(UUID id);

    ResponseEntity<ApiResponse<TacheResponse>> findBySignalementId(UUID signalementId);

    ResponseEntity<ApiResponse<TacheResponse>> updateTache(UUID id, TacheRequest request, MultipartFile fichierDevis);
    ResponseEntity<ApiResponse<TacheResponse>> updateTacheResolu(UUID id);
    ResponseEntity<ApiResponse<TacheResponse>> updateTacheActiver(UUID id);

 ResponseEntity<ApiResponse<Void>> delete(UUID id);
}
