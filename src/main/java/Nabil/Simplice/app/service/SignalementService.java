package Nabil.Simplice.app.service;

import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.dto.request.SignalementRequest;
import Nabil.Simplice.app.dto.response.SignalementResponse;
import Nabil.Simplice.app.dto.response.DevisResponse;
import Nabil.Simplice.app.enums.StatutSignalement;
import Nabil.Simplice.app.enums.TypeService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SignalementService {
    // Créer un nouveau signalement
    ResponseEntity<ApiResponse<SignalementResponse>> creerSignalement(SignalementRequest request);

    // nouvelle methode de creation
    public ResponseEntity<ApiResponse<SignalementResponse>> creerSignalementComplet(SignalementRequest request , List<MultipartFile> fichiers);

    // Récupérer tous les signalements
    ResponseEntity<ApiResponse<List<SignalementResponse>>> getAll();

    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getAllByOuvrier(UUID id);

    // Récupérer un signalement par son id
    ResponseEntity<ApiResponse<SignalementResponse>> getById(UUID id);

    // Mettre à jour un signalement
    ResponseEntity<ApiResponse<SignalementResponse>> updateSignalement(UUID id, SignalementRequest request);

    // Supprimer un signalement
    ResponseEntity<ApiResponse<Void>> delete(UUID id);

    // Mettre à jour le statut d'un signalement
    SignalementResponse updateStatut(Long id, StatutSignalement statut);

    // Ajouter un commentaire de service
    SignalementResponse ajouterCommentaireService(Long id, String commentaire);

    // Récupérer les signalements par type de service
    List<SignalementResponse> getSignalementsByTypeService(TypeService typeService);

    // Récupérer les signalements par statut
    List<SignalementResponse> getSignalementsByStatut(StatutSignalement statut);

    // Récupérer les signalements par priorité
    List<SignalementResponse> getSignalementsByPriorite(Integer priorite);

    // Récupérer les signalements par service
    List<SignalementResponse> getSignalementsByServiceId(Long serviceId);

    // Finaliser un signalement (changer le statut vers TRAITE)
    ResponseEntity<ApiResponse<SignalementResponse>> finaliserSignalement(String trackingId);

    // Créer un nouveau signalement avec image
//    SignalementResponse creerSignalementAvecImage(SignalementRequestRequest request, MultipartFile image);

    // Upload d'un devis PDF
    ResponseEntity<ApiResponse<String>> uploadDevis(MultipartFile pdf, String titre, String signalementId, String ouvrierId, String dateCreation);

    // Récupérer les devis d'un signalement
    ResponseEntity<ApiResponse<List<DevisResponse>>> getDevisBySignalement(String signalementId);

    // Télécharger un devis PDF
    ResponseEntity<byte[]> downloadDevisPdf(String devisId);

    // Associer un devis à un signalement
    ResponseEntity<ApiResponse<String>> associerDevis(String signalementId, String fichierId);

    ResponseEntity<ApiResponse<String>> activerSignalement(UUID id);
    ResponseEntity<ApiResponse<String>> rejeterSignalement(UUID id);
    ResponseEntity<ApiResponse<String>> ajouterRapportAvancement(UUID id, MultipartFile pdf, String commentaire);
    ResponseEntity<ApiResponse<List<DevisResponse>>> getRapportsBySignalement(String signalementId);
}
