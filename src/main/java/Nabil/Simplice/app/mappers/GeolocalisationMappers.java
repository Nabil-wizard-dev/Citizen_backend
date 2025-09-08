package Nabil.Simplice.app.mappers;

import org.springframework.stereotype.Component;
import Nabil.Simplice.app.entity.Geolocalisation;
import Nabil.Simplice.app.dto.request.GeolocalisationRequest;
import Nabil.Simplice.app.dto.response.GeolocalisationResponse;

@Component
public class GeolocalisationMappers {

    public Geolocalisation toEntity(GeolocalisationRequest request) {
        Geolocalisation geo = new Geolocalisation();
        geo.setLatitude(request.getLatitude());
        geo.setLongitude(request.getLongitude());
        return geo;
    }

    public GeolocalisationResponse toResponse(Geolocalisation geo) {
        GeolocalisationResponse response = new GeolocalisationResponse();
        response.setId(geo.getId());
        response.setLatitude(geo.getLatitude());
        response.setLongitude(geo.getLongitude());
        return response;
    }
}
