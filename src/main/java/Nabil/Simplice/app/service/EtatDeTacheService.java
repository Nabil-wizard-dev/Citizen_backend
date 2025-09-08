package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.EtatDeTacheRequest;
import Nabil.Simplice.app.dto.response.EtatDeTacheResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
public interface EtatDeTacheService {
    ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTache(EtatDeTacheRequest request);

    // nouvelle methode de creation
//    ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTacheComplet(EtatDeTacheRequest request, List<MultipartFile> fichiers);
    ResponseEntity<ApiResponse<EtatDeTacheResponse>> creerEtatDeTacheComplet(EtatDeTacheRequest request, MultipartFile fichier);

    ResponseEntity<ApiResponse<List<EtatDeTacheResponse>>> getAll();

    ResponseEntity<ApiResponse<EtatDeTacheResponse>> getById(UUID id);

    ResponseEntity<ApiResponse<List<EtatDeTacheResponse>>> findByTacheId(UUID TacheId);

    ResponseEntity<ApiResponse<EtatDeTacheResponse>> updateEtatDeTache(UUID id, EtatDeTacheRequest request);

    ResponseEntity<ApiResponse<Void>> delete(UUID id);
}
