package Nabil.Simplice.app.service;

import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import Nabil.Simplice.app.dto.response.FichierJoinResponse;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FichierJoinService {
//    FichierJoinResponse saveFichier(MultipartFile file, Long signalementId);
//    Optional<FichierJoinResponse> getFichierById(Long id);
//    List<FichierJoinResponse> getFichiersBySignalement(Long signalementId);
//    void deleteFichier(Long id);

    ResponseEntity<ApiResponse<String>> uploadFichier(MultipartFile Fichier);
}
