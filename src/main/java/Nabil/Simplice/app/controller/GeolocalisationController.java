package Nabil.Simplice.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import Nabil.Simplice.app.service.GeolocalisationService;
import Nabil.Simplice.app.dto.request.GeolocalisationRequest;
import Nabil.Simplice.app.dto.response.GeolocalisationResponse;

@RestController
@RequestMapping("/api/geolocalisations")
public class GeolocalisationController {

    private final GeolocalisationService service;

    @Autowired
    public GeolocalisationController(GeolocalisationService service) {
        this.service = service;
    }

    @PostMapping
    public GeolocalisationResponse creer(@RequestBody GeolocalisationRequest request) {
        return service.creer(request);
    }

    @GetMapping
    public List<GeolocalisationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GeolocalisationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public GeolocalisationResponse update(@PathVariable Long id, @RequestBody GeolocalisationRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
