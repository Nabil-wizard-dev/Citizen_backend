package Nabil.Simplice.app.service;

import java.util.List;
import Nabil.Simplice.app.dto.request.GeolocalisationRequest;
import Nabil.Simplice.app.dto.response.GeolocalisationResponse;

public interface GeolocalisationService {
    // Créer une géolocalisation
    GeolocalisationResponse creer(GeolocalisationRequest request);

    // Récupérer toutes les géolocalisations
    List<GeolocalisationResponse> getAll();

    // Récupérer une géolocalisation par id
    GeolocalisationResponse getById(Long id);

    // Mettre à jour une géolocalisation
    GeolocalisationResponse update(Long id, GeolocalisationRequest request);

    // Supprimer une géolocalisation
    void delete(Long id);
}
