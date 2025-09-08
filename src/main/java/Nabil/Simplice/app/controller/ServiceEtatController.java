package Nabil.Simplice.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Nabil.Simplice.app.service.ServiceEtatService;
import Nabil.Simplice.app.entity.ServiceEtat;
import Nabil.Simplice.app.enums.TypeService;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceEtatController {

    private final ServiceEtatService serviceEtatService;

    public ServiceEtatController(ServiceEtatService serviceEtatService) {
        this.serviceEtatService = serviceEtatService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceEtat>> getAllServices() {
        List<ServiceEtat> services = serviceEtatService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEtat> getServiceById(@PathVariable Long id) {
        return serviceEtatService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ServiceEtat>> getServicesByType(@PathVariable TypeService type) {
        List<ServiceEtat> services = serviceEtatService.getServicesByType(type);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<ServiceEtat>> getServicesByZone(@PathVariable String zone) {
        List<ServiceEtat> services = serviceEtatService.getServicesByZoneCouverture(zone);
        return ResponseEntity.ok(services);
    }

    @PostMapping
    public ResponseEntity<ServiceEtat> createService(@RequestBody ServiceEtat service) {
        ServiceEtat savedService = serviceEtatService.saveService(service);
        return ResponseEntity.ok(savedService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEtat> updateService(
            @PathVariable Long id,
            @RequestBody ServiceEtat service) {
        if (!serviceEtatService.getServiceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.setId(id);
        ServiceEtat updatedService = serviceEtatService.saveService(service);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (!serviceEtatService.getServiceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serviceEtatService.deleteService(id);
        return ResponseEntity.ok().build();
    }
} 