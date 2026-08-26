package Nabil.Simplice.app.controller;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.service.SondageService;
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
@RequestMapping("/api/sondages")
public class SondageController {

    private final SondageService sondageService;

    public SondageController(SondageService sondageService) {
        this.sondageService = sondageService;
    }

    @GetMapping("getAll")
    public ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondages(){
        return sondageService.getAllSondages();
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ApiResponse<SondageResponse>> getSondageById(@PathVariable UUID id){
        return sondageService.getSondageById(id);
    }

    @GetMapping("getBySignalement/{signalementId}")
    public ResponseEntity<ApiResponse<SondageResponse>> getSondageBySignalement(@PathVariable UUID signalementId){
        return sondageService.getSondageBySignalement(signalementId);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<SondageResponse>> addSondage(@Valid @RequestBody SondageRequest request){
        return sondageService.addSondage(request);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<SondageResponse>> updateSondage(@PathVariable UUID id,
                                                                      @RequestBody SondageRequest request){
        return sondageService.updateSondage(id, request);
    }

    @PostMapping("cloturer/{id}")
    public ResponseEntity<ApiResponse<SondageResponse>> cloturerSondage(@PathVariable UUID id){
        return sondageService.cloturerSondage(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSondage(@PathVariable UUID id){
        return sondageService.deleteSondage(id);
    }
}
