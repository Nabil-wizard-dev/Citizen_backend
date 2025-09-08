package Nabil.Simplice.app.controller;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Nabil.Simplice.app.dto.response.FichierJoinResponse;
import Nabil.Simplice.app.service.FichierJoinService;

@RestController
@RequestMapping("/api/fichiers")
public class FichierJoinController {
    
    private final FichierJoinService fichierJoinService;

    public FichierJoinController(FichierJoinService fichierJoinService) {
        this.fichierJoinService = fichierJoinService;
    }

//    @PostMapping
//    public ResponseEntity<FichierJoinResponse> uploadFichier(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("signalementId") Long signalementId) {
//        return ResponseEntity.ok(fichierJoinService.saveFichier(file, signalementId));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<FichierJoinResponse> getFichierById(@PathVariable Long id) {
//        return fichierJoinService.getFichierById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/signalement/{signalementId}")
//    public ResponseEntity<List<FichierJoinResponse>> getFichiersBySignalement(@PathVariable Long signalementId) {
//        return ResponseEntity.ok(fichierJoinService.getFichiersBySignalement(signalementId));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFichier(@PathVariable Long id) {
//        fichierJoinService.deleteFichier(id);
//        return ResponseEntity.ok().build();
//    }


    //end point pour l'upload de tous type
    @PostMapping("/Files")
    public ResponseEntity<Nabil.Simplice.app.utils.ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file)  {
        return fichierJoinService.uploadFichier(file);
    }
}
