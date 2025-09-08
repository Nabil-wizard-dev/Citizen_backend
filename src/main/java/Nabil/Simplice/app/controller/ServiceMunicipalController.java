package Nabil.Simplice.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Nabil.Simplice.app.dto.request.ServiceMunicipalRequest;
import Nabil.Simplice.app.dto.response.ServiceMunicipalResponse;
import Nabil.Simplice.app.service.ServiceMunicipalService;

import java.util.List;

@RestController
@RequestMapping("/api/services-municipaux")
public class ServiceMunicipalController {

    private final ServiceMunicipalService serviceMunicipalService;

    public ServiceMunicipalController(ServiceMunicipalService serviceMunicipalService) {
        this.serviceMunicipalService = serviceMunicipalService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceMunicipalResponse>> getAllServices() {
        return ResponseEntity.ok(serviceMunicipalService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceMunicipalResponse> getServiceById(@PathVariable Long id) {
        return serviceMunicipalService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceMunicipalResponse> createService(@RequestBody ServiceMunicipalRequest request) {
        return ResponseEntity.ok(serviceMunicipalService.createService(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceMunicipalResponse> updateService(
            @PathVariable Long id,
            @RequestBody ServiceMunicipalRequest request) {
        return ResponseEntity.ok(serviceMunicipalService.updateService(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceMunicipalService.deleteService(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<ServiceMunicipalResponse>> getServicesByZone(@PathVariable String zone) {
        return ResponseEntity.ok(serviceMunicipalService.getServicesByZone(zone));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ServiceMunicipalResponse>> getServicesByType(@PathVariable String type) {
        return ResponseEntity.ok(serviceMunicipalService.getServicesByType(type));
    }

    @GetMapping("/{id}/est-ouvert")
    public ResponseEntity<Boolean> isServiceOuvert(@PathVariable Long id) {
        return ResponseEntity.ok(serviceMunicipalService.isServiceOuvert(id));
    }

    @GetMapping("/budget-superieur/{budget}")
    public ResponseEntity<List<ServiceMunicipalResponse>> getServicesByBudgetSuperieurA(
            @PathVariable Double budget) {
        return ResponseEntity.ok(serviceMunicipalService.getServicesByBudgetSuperieurA(budget));
    }
} 