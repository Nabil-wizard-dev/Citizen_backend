package Nabil.Simplice.app.serviceImpl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import Nabil.Simplice.app.service.GeolocalisationService;
import Nabil.Simplice.app.repository.GeolocalisationRepository;
import Nabil.Simplice.app.mappers.GeolocalisationMappers;
import Nabil.Simplice.app.entity.Geolocalisation;
import Nabil.Simplice.app.dto.request.GeolocalisationRequest;
import Nabil.Simplice.app.dto.response.GeolocalisationResponse;

@Service
public class GeolocalisationServiceImpl implements GeolocalisationService {

    private final GeolocalisationRepository repository;
    private final GeolocalisationMappers mappers;

    
    public GeolocalisationServiceImpl(GeolocalisationRepository repository, GeolocalisationMappers mappers) {
        this.repository = repository;
        this.mappers = mappers;
    }

    @Override
    public GeolocalisationResponse creer(GeolocalisationRequest request) {
        Geolocalisation geo = mappers.toEntity(request);
        Geolocalisation saved = repository.save(geo);
        return mappers.toResponse(saved);
    }

    @Override
    public List<GeolocalisationResponse> getAll() {
        return repository.findAll().stream()
                .map(mappers::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GeolocalisationResponse getById(Long id) {
        return repository.findById(id)
                .map(mappers::toResponse)
                .orElse(null);
    }

    @Override
    public GeolocalisationResponse update(Long id, GeolocalisationRequest request) {
        return repository.findById(id)
                .map(geo -> {
                    geo.setLatitude(request.getLatitude());
                    geo.setLongitude(request.getLongitude());
                    return mappers.toResponse(repository.save(geo));
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}