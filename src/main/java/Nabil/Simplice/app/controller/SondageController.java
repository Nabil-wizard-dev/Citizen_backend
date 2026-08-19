package Nabil.Simplice.app.controller;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.service.SondageService;
import Nabil.Simplice.app.utils.ApiResponse;
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

    @GetMapping("getAllByEntite/{entityId}")
    public ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondagesByEntityId(@PathVariable UUID entityId){
        return sondageService.getAllSondagesByEntityId(entityId);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ApiResponse<SondageResponse>> getSondageById(@PathVariable UUID id){
        return sondageService.getSondageById(id);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<SondageResponse>> addSondage(@RequestBody SondageRequest a){
        return sondageService.addSondage(a);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<SondageResponse>> updateSondage(@PathVariable UUID id,
                                                                      @RequestBody SondageRequest a){
        return sondageService.updateSondage(id,a);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSondage(@PathVariable UUID id){
        return sondageService.deleteSondage(id);
    }
}
