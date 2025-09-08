package Nabil.Simplice.app.controller;

import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import Nabil.Simplice.app.dto.request.SignalementRequest;
import Nabil.Simplice.app.dto.response.SignalementResponse;
import Nabil.Simplice.app.dto.response.DevisResponse;
import Nabil.Simplice.app.enums.StatutSignalement;
import Nabil.Simplice.app.enums.TypeService;
import Nabil.Simplice.app.service.SignalementService;

@RestController
@RequestMapping("/api/signalements")
public class SignalementController {
    private final SignalementService signalementService;

    public SignalementController(SignalementService signalementService) {
        this.signalementService = signalementService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getAllSignalements() {
        return signalementService.getAll();
    }

    @GetMapping("allByOuvrier/{id}")
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getAllSignalementsByOuvrier( @PathVariable UUID id) {
        return signalementService.getAllByOuvrier(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SignalementResponse>> getSignalementById(
        @PathVariable UUID id) {
            return signalementService.getById(id);

    }


    // @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<ApiResponse<SignalementResponse>> createSignalementWithFiles(
    //     @RequestPart("request") SignalementRequest request,
    //     @RequestPart("files") List<MultipartFile> fichiers) {
    //     return signalementService.creerSignalement(request, fichiers);
    // }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SignalementResponse>> createSignalementWithFilesAlias(
        @RequestPart("request") SignalementRequest request,
        @RequestPart("files") List<MultipartFile> fichiers) {
        return signalementService.creerSignalement(request, fichiers);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<SignalementResponse>> updateSignalement(
            @PathVariable UUID id,
            @RequestBody SignalementRequest request) {
        return signalementService.updateSignalement(id, request);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSignalement(@PathVariable UUID id) {
        return signalementService.delete(id);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByStatut(
        @PathVariable StatutSignalement statut) {
        return signalementService.getSignalementsByStatut(statut);
    }

    @GetMapping("/type/{typeService}")
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByType(
        @PathVariable TypeService typeService) {
        return signalementService.getSignalementsByTypeService(typeService);
    }

    @GetMapping("/priorite/{priorite}")
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByPriorite(@PathVariable Integer priorite) {
        return signalementService.getSignalementsByPriorite(priorite);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByService(@PathVariable Long serviceId) {
        return signalementService.getSignalementsByServiceId(serviceId);
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<ApiResponse<SignalementResponse>> updateStatutSignalement(
            @PathVariable UUID id,
            @RequestParam StatutSignalement statut) {
        return signalementService.updateStatut(id, statut);
    }

    @PatchMapping("/{id}/commentaire")
    public ResponseEntity<ApiResponse<SignalementResponse>> ajouterCommentaire(
            @PathVariable UUID id,
            @RequestParam String commentaire) {
        return signalementService.ajouterCommentaireService(id, commentaire);
    }

    @PostMapping("/finaliser/{trackingId}")
    public ResponseEntity<ApiResponse<SignalementResponse>> finaliserSignalement(
            @PathVariable String trackingId) {
        return signalementService.finaliserSignalement(trackingId);
    }

    // Endpoint pour uploader un devis PDF
    @PostMapping(value = "uploadDevis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadDevis(
            @RequestPart("pdf") MultipartFile pdf,
            @RequestParam("titre") String titre,
            @RequestParam("signalementId") String signalementId,
            @RequestParam("ouvrierId") String ouvrierId,
            @RequestParam("dateCreation") String dateCreation) {
        return signalementService.uploadDevis(pdf, titre, signalementId, ouvrierId, dateCreation);
    }

    // Endpoint pour associer un devis à un signalement
    @PostMapping("associerDevis/{signalementId}/{fichierId}")
    public ResponseEntity<ApiResponse<String>> associerDevis(
            @PathVariable String signalementId,
            @PathVariable String fichierId) {
        return signalementService.associerDevis(signalementId, fichierId);
    }

    // Endpoint pour récupérer les devis d'un signalement
    @GetMapping("devis/{signalementId}")
    public ResponseEntity<ApiResponse<List<DevisResponse>>> getDevisBySignalement(@PathVariable String signalementId) {
        return signalementService.getDevisBySignalement(signalementId);
    }

    // Endpoint pour télécharger un devis PDF
    @GetMapping("devis/{devisId}/pdf")
    public ResponseEntity<byte[]> downloadDevisPdf(@PathVariable String devisId) {
        return signalementService.downloadDevisPdf(devisId);
    }

    @PostMapping("/activer/{id}")
    public ResponseEntity<ApiResponse<String>> activerSignalement(@PathVariable UUID id) {
        return signalementService.activerSignalement(id);
    }

    @PostMapping("/rejeter/{id}")
    public ResponseEntity<ApiResponse<String>> rejeterSignalement(@PathVariable UUID id) {
        return signalementService.rejeterSignalement(id);
    }

    @PostMapping(value = "/rapport/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> rapportAvancement(
        @PathVariable UUID id,
        @RequestPart("pdf") MultipartFile pdf,
        @RequestParam("commentaire") String commentaire
    ) {
        return signalementService.ajouterRapportAvancement(id, pdf, commentaire);
    }

    @GetMapping("/rapports/{signalementId}")
    public ResponseEntity<ApiResponse<List<DevisResponse>>> getRapportsBySignalement(@PathVariable String signalementId) {
        return signalementService.getRapportsBySignalement(signalementId);
    }
}
