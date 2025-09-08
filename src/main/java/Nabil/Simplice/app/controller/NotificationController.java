package Nabil.Simplice.app.controller;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.request.OuvrierRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.dto.response.OuvrierResponse;
import Nabil.Simplice.app.service.NotificationService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllOuvriers() {
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getOuvrierById(@PathVariable UUID id) {
        return notificationService.getById(id);
    }

    @GetMapping("byDestinataire/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getOuvrierByDestinataire(@PathVariable UUID id) {
        return notificationService.getByDistinataire(id);
    }

    @GetMapping("byEntite/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getOuvrierByEntite(@PathVariable UUID id) {
        return notificationService.getByEntite(id);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<NotificationResponse>> createOuvrier(@RequestBody NotificationRequest request) {
        return notificationService.creerNotification(request);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> updateOuvrier(
            @PathVariable UUID id,
            @RequestBody NotificationRequest request) {
        return notificationService.updateNotification(id, request);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOuvrier(@PathVariable UUID id) {
        return notificationService.delete(id);
    }

}
