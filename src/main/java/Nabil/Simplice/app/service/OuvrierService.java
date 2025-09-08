package Nabil.Simplice.app.service;

import java.util.List;
import java.util.UUID;

import Nabil.Simplice.app.dto.request.OuvrierRequest;
import Nabil.Simplice.app.dto.response.OuvrierResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface OuvrierService {
    // Créer un nouvel ouvrier
    ResponseEntity<ApiResponse<OuvrierResponse>> creerOuvrier(OuvrierRequest request);

    // Récupérer tous les ouvriers
    ResponseEntity<ApiResponse<List<OuvrierResponse>>> getAll();

    // Récupérer un ouvrier par son id
    ResponseEntity<ApiResponse<OuvrierResponse>> getById(UUID id);

    // Mettre à jour un ouvrier
    ResponseEntity<ApiResponse<OuvrierResponse>> updateOuvrier(UUID id, OuvrierRequest request);
    ResponseEntity<ApiResponse<OuvrierResponse>> updateOuvrierSignalementActuId(UUID id, UUID signalementActuelId);

    // Supprimer un ouvrier
    ResponseEntity<ApiResponse<Void>> delete(UUID id);

    // Récupérer les ouvriers par spécialité
//    List<OuvrierResponse> getOuvriersBySpecialite(specilateOuvriere specialite);
//
//    // Récupérer les ouvriers par service
//    List<OuvrierResponse> getOuvriersByServiceId(Long serviceId);
//
//    // Récupérer les ouvriers disponibles (sans signalement actuel)
//    List<OuvrierResponse> getOuvriersDisponibles();
//
//    // Mettre à jour la spécialité d'un ouvrier
//    OuvrierResponse updateSpecialite(UUID id, specilateOuvriere specialite);
//
//    // Mettre à jour le service d'un ouvrier
//    OuvrierResponse updateService(UUID id, Long serviceId);
//
//    // Mettre à jour le signalement actuel d'un ouvrier
//    OuvrierResponse updateSignalementActuel(UUID id, UUID signalementId);

    // (Ajouter ici d'autres méthodes spécifiques si besoin, ex: recherche par spécialité, par service, etc.)
}
