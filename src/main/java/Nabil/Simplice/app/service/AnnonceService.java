package Nabil.Simplice.app.service;


import Nabil.Simplice.app.dto.request.AnnonceRequest;
import Nabil.Simplice.app.dto.response.AnnonceResponse;
import Nabil.Simplice.app.entity.Annonce;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AnnonceService {
    ResponseEntity<ApiResponse<List<AnnonceResponse>>> getAllAnnonces();
    ResponseEntity<ApiResponse<List<AnnonceResponse>>> getAllAnnoncesByEntityId(UUID entityId);
    ResponseEntity<ApiResponse<AnnonceResponse>> getAnnonceById(UUID id);
    ResponseEntity<ApiResponse<AnnonceResponse>> addAnnonce(AnnonceRequest a, List<MultipartFile> fichiers);
    ResponseEntity<ApiResponse<AnnonceResponse>> updateAnnonce(UUID id, AnnonceRequest a);
    ResponseEntity<ApiResponse<Void>> deleteAnnonce(UUID id);

}
