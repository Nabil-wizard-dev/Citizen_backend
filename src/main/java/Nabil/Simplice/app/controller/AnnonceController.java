package Nabil.Simplice.app.controller;

import Nabil.Simplice.app.dto.request.AnnonceRequest;
import Nabil.Simplice.app.dto.response.AnnonceResponse;
import Nabil.Simplice.app.service.AnnonceService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    private AnnonceService annonceService;

    public AnnonceController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    @GetMapping("getAll")
    public ResponseEntity<ApiResponse<List<AnnonceResponse>>> getAllAnnonces(){
        return annonceService.getAllAnnonces();
    }

    @GetMapping("getAllByEntite/{entityId}")
    public ResponseEntity<ApiResponse<List<AnnonceResponse>>> getAllAnnoncesByEntityId(@PathVariable UUID entityId){
        return annonceService.getAllAnnoncesByEntityId(entityId);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ApiResponse<AnnonceResponse>> getAnnonceById(@PathVariable UUID id){
        return annonceService.getAnnonceById(id);
    }

    @PostMapping(value ="add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AnnonceResponse>> addAnnonce(@RequestPart("request") AnnonceRequest a,
                                                                   @RequestPart("files") List<MultipartFile> fichiers){
        return annonceService.addAnnonce(a,fichiers);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<AnnonceResponse>> updateAnnonce(@PathVariable UUID id,
                                                                      @RequestBody AnnonceRequest a){
        return annonceService.updateAnnonce(id,a);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAnnonce(@PathVariable UUID id){
        return annonceService.deleteAnnonce(id);
    }
}
