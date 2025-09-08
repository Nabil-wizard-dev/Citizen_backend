package Nabil.Simplice.app.controller;

import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Nabil.Simplice.app.dto.request.OuvrierRequest;
import Nabil.Simplice.app.dto.response.OuvrierResponse;
import Nabil.Simplice.app.enums.specilateOuvriere;
import Nabil.Simplice.app.service.OuvrierService;

@RestController
@RequestMapping("/api/ouvriers")
public class OuvrierController {
    private final OuvrierService ouvrierService;

    public OuvrierController(OuvrierService ouvrierService) {
        this.ouvrierService = ouvrierService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<OuvrierResponse>>> getAllOuvriers() {
        return ouvrierService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OuvrierResponse>> getOuvrierById(@PathVariable UUID id) {
        return ouvrierService.getById(id);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<OuvrierResponse>> createOuvrier(@RequestBody OuvrierRequest request) {
        return ouvrierService.creerOuvrier(request);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<OuvrierResponse>> updateOuvrier(
            @PathVariable UUID id,
            @RequestBody OuvrierRequest request) {
        return ouvrierService.updateOuvrier(id, request);
    }

    @PutMapping("updateOuvrierSignalementActuId/{id}")
    public ResponseEntity<ApiResponse<OuvrierResponse>> updateOuvrierSignalementActuId(
            @PathVariable UUID id,
            @RequestBody String signalementActuelId){
        UUID signalementUUID = UUID.fromString(signalementActuelId);
        return ouvrierService.updateOuvrierSignalementActuId(id, signalementUUID);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOuvrier(@PathVariable UUID id) {
        return ouvrierService.delete(id);
    }

//    @GetMapping("/specialite/{specialite}")
//    public ResponseEntity<List<OuvrierResponse>> getOuvriersBySpecialite(@PathVariable specilateOuvriere specialite) {
//        // Cette méthode nécessitera d'être implémentée dans le service
//        return ResponseEntity.ok(ouvrierService.getOuvriersBySpecialite(specialite));
//    }
//
//    @GetMapping("/service/{serviceId}")
//    public ResponseEntity<List<OuvrierResponse>> getOuvriersByService(@PathVariable Long serviceId) {
//        // Cette méthode nécessitera d'être implémentée dans le service
//        return ResponseEntity.ok(ouvrierService.getOuvriersByServiceId(serviceId));
//    }
//
//    @GetMapping("/disponibles")
//    public ResponseEntity<List<OuvrierResponse>> getOuvriersDisponibles() {
//        // Cette méthode nécessitera d'être implémentée dans le service
//        return ResponseEntity.ok(ouvrierService.getOuvriersDisponibles());
//    }

//    @PatchMapping("/{id}/specialite")
//    public ResponseEntity<OuvrierResponse> updateSpecialiteOuvrier(
//            @PathVariable UUID id,
//            @RequestParam specilateOuvriere specialite) {
//        // Cette méthode nécessitera d'être implémentée dans le service
//        return ResponseEntity.ok(ouvrierService.updateSpecialite(id, specialite));
//    }
//
//    @PatchMapping("/{id}/service")
//    public ResponseEntity<OuvrierResponse> updateServiceOuvrier(
//            @PathVariable UUID id,
//            @RequestParam Long serviceId) {
//        // Cette méthode nécessitera d'être implémentée dans le service
//        return ResponseEntity.ok(ouvrierService.updateService(id, serviceId));
//    }
//
//    @PatchMapping("/{id}/signalement-actuel")
//    public ResponseEntity<OuvrierResponse> updateSignalementActuel(
//            @PathVariable UUID id,
//            @RequestParam UUID signalementId) {
//        // Cette méthode nécessitera d'être implémentée dans le service
//        return ResponseEntity.ok(ouvrierService.updateSignalementActuel(id, signalementId));
//    }
}
