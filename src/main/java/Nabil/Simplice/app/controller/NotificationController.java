package Nabil.Simplice.app.controller;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.service.NotificationService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications() {
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotificationById(@PathVariable UUID id) {
        return notificationService.getById(id);
    }

    /** Boite de reception d'un utilisateur : le plus recent en premier. */
    @GetMapping("byDestinataire/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationsByDestinataire(@PathVariable UUID id) {
        return notificationService.getByDistinataire(id);
    }

    @GetMapping("byDestinataire/{id}/nonLues")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNonLuesByDestinataire(@PathVariable UUID id) {
        return notificationService.getNonLuesByDestinataire(id);
    }

    /** Alimente la pastille de compteur du front sans transferer toute la liste. */
    @GetMapping("byDestinataire/{id}/compteur")
    public ResponseEntity<ApiResponse<Long>> compterNonLues(@PathVariable UUID id) {
        return notificationService.compterNonLues(id);
    }

    @GetMapping("byEntite/{id}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationsByEntite(@PathVariable UUID id) {
        return notificationService.getByEntite(id);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@RequestBody NotificationRequest request) {
        return notificationService.creerNotification(request);
    }

    @PutMapping("marquerLu/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> marquerLu(@PathVariable UUID id) {
        return notificationService.marquerLu(id);
    }

    @PutMapping("byDestinataire/{id}/marquerToutesLues")
    public ResponseEntity<ApiResponse<Integer>> marquerToutesLues(@PathVariable UUID id) {
        return notificationService.marquerToutesLues(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> updateNotification(
            @PathVariable UUID id,
            @RequestBody NotificationRequest request) {
        return notificationService.updateNotification(id, request);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable UUID id) {
        return notificationService.delete(id);
    }

}
