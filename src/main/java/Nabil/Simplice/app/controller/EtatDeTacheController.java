package Nabil.Simplice.app.controller;

import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.dto.request.SignalementRequest;
import Nabil.Simplice.app.dto.response.SignalementResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Nabil.Simplice.app.dto.request.EtatDeTacheRequest;
import Nabil.Simplice.app.dto.response.EtatDeTacheResponse;
import Nabil.Simplice.app.service.EtatDeTacheService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/etatsDeTaches")
public class EtatDeTacheController {
    private final EtatDeTacheService etatDeTacheService;

    public EtatDeTacheController(EtatDeTacheService etatDeTacheService) {
        this.etatDeTacheService = etatDeTacheService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<EtatDeTacheResponse>>> getAllEtatsDeTache() {
        return etatDeTacheService.getAll();
    }

    @GetMapping("allByTache/{id}")
    public ResponseEntity<ApiResponse<List<EtatDeTacheResponse>>> getAllEtatsOfTache(@PathVariable UUID id) {
        return etatDeTacheService.findByTacheId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> getEtatDeTacheById(@PathVariable UUID id) {
        return etatDeTacheService.getById(id);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> createEtatDeTache(@RequestBody EtatDeTacheRequest request) {
        return etatDeTacheService.creerEtatDeTache(request);
    }

    @PostMapping(value = "addInOne",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> creationEtatDeTacheComplete(
            @RequestPart("request") EtatDeTacheRequest request,
            @RequestPart("files") MultipartFile fichiers) {
        return etatDeTacheService.creerEtatDeTacheComplet(request,fichiers);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<EtatDeTacheResponse>> updateEtatDeTache(
            @PathVariable UUID id,
            @RequestBody EtatDeTacheRequest request) {
        return etatDeTacheService.updateEtatDeTache(id, request);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEtatDeTache(@PathVariable UUID id) {
        return etatDeTacheService.delete(id);
    }
}
