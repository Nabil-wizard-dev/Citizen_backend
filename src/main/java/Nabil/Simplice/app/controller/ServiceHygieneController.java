package Nabil.Simplice.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Nabil.Simplice.app.dto.request.ServiceHygieneRequest;
import Nabil.Simplice.app.dto.response.ServiceHygieneResponse;
import Nabil.Simplice.app.service.ServiceHygieneService;

import java.util.List;

@RestController
@RequestMapping("/api/services-hygiene")
public class ServiceHygieneController {

    private final ServiceHygieneService serviceHygieneService;

    public ServiceHygieneController(ServiceHygieneService serviceHygieneService) {
        this.serviceHygieneService = serviceHygieneService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceHygieneResponse>> getAllServices() {
        return ResponseEntity.ok(serviceHygieneService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceHygieneResponse> getServiceById(@PathVariable Long id) {
        return serviceHygieneService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceHygieneResponse> createService(@RequestBody ServiceHygieneRequest request) {
        return ResponseEntity.ok(serviceHygieneService.createService(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceHygieneResponse> updateService(
            @PathVariable Long id,
            @RequestBody ServiceHygieneRequest request) {
        return ResponseEntity.ok(serviceHygieneService.updateService(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceHygieneService.deleteService(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<ServiceHygieneResponse>> getServicesByZone(@PathVariable String zone) {
        return ResponseEntity.ok(serviceHygieneService.getServicesByZone(zone));
    }

    @GetMapping("/type-inspection/{type}")
    public ResponseEntity<List<ServiceHygieneResponse>> getServicesByTypeInspection(@PathVariable String type) {
        return ResponseEntity.ok(serviceHygieneService.getServicesByTypeInspection(type));
    }

    @GetMapping("/{id}/est-ouvert")
    public ResponseEntity<Boolean> isServiceOuvert(@PathVariable Long id) {
        return ResponseEntity.ok(serviceHygieneService.isServiceOuvert(id));
    }
} 