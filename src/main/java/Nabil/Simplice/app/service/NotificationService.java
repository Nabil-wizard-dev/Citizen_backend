package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.request.SignalementRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.dto.response.SignalementResponse;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    ResponseEntity<ApiResponse<NotificationResponse>> creerNotification(NotificationRequest request);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getAll();
    ResponseEntity<ApiResponse<NotificationResponse>> getById(UUID id);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getByEntite(UUID id);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getByDistinataire(UUID id);
    ResponseEntity<ApiResponse<NotificationResponse>> updateNotification(UUID id, NotificationRequest request);
    ResponseEntity<ApiResponse<Void>> delete(UUID id);

}
