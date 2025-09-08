package Nabil.Simplice.app.controller;

import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Nabil.Simplice.app.dto.request.TacheRequest;
import Nabil.Simplice.app.dto.response.TacheResponse;
import Nabil.Simplice.app.service.TacheService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/taches")
public class TacheController {
    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<TacheResponse>>> getAllTaches() {
        return tacheService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<TacheResponse>> getTacheById(@PathVariable UUID id) {
        return tacheService.getById(id);
    }

    @GetMapping("bysignalement/{id}")
    public ResponseEntity<ApiResponse<TacheResponse>> getTacheBySignalementId(@PathVariable UUID id) {
        return tacheService.findBySignalementId(id);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<TacheResponse>> createTache(@RequestBody TacheRequest request) {
        return tacheService.creerTache(request);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<TacheResponse>> updateTache(
            @PathVariable UUID id,
            @RequestPart(value = "request") TacheRequest request,
            @RequestPart(value = "fichierDevis", required = false) MultipartFile fichierDevis) {
        return tacheService.updateTache(id, request, fichierDevis);
    }

    @GetMapping("setResolu/{id}")
    public ResponseEntity<ApiResponse<TacheResponse>> updateTacheResolu(@PathVariable UUID id){
        return tacheService.updateTacheResolu(id);
    }

    @GetMapping("setActiver/{id}")
    public ResponseEntity<ApiResponse<TacheResponse>> updateTacheActiver(@PathVariable UUID id){
        return tacheService.updateTacheActiver(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTache(@PathVariable UUID id) {
        return tacheService.delete(id);
    }
}
