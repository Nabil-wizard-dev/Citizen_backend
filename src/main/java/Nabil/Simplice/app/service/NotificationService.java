package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    ResponseEntity<ApiResponse<NotificationResponse>> creerNotification(NotificationRequest request);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getAll();
    ResponseEntity<ApiResponse<NotificationResponse>> getById(UUID id);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getByEntite(UUID id);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getByDistinataire(UUID id);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getNonLuesByDestinataire(UUID id);
    ResponseEntity<ApiResponse<Long>> compterNonLues(UUID destinataireId);
    ResponseEntity<ApiResponse<NotificationResponse>> marquerLu(UUID id);
    ResponseEntity<ApiResponse<Integer>> marquerToutesLues(UUID destinataireId);
    ResponseEntity<ApiResponse<NotificationResponse>> updateNotification(UUID id, NotificationRequest request);
    ResponseEntity<ApiResponse<Void>> delete(UUID id);

}
