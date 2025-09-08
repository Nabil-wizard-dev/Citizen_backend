package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.dto.response.SignalementResponse;
import Nabil.Simplice.app.entity.Notification;
import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.mappers.NotificationMappers;
import Nabil.Simplice.app.repository.NotificationRepository;
import Nabil.Simplice.app.service.NotificationService;
import Nabil.Simplice.app.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationMappers mappers;


    public NotificationServiceImpl(NotificationRepository repository, NotificationMappers mappers) {
        this.repository = repository;
        this.mappers = mappers;
    }

    @Override
    public ResponseEntity<ApiResponse<NotificationResponse>> creerNotification(NotificationRequest request) {
        try {
            Notification saved = repository.save(mappers.toEntity(request));

            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la creation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAll() {
        try {
            List<NotificationResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<NotificationResponse>> getById(UUID id) {
        try {
            NotificationResponse Response = repository.findByTrackingId(id)
                    .map(mappers::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", Response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getByEntite(UUID id) {
        try {
            List<NotificationResponse> responses = Optional.of(repository.findByEntiteId(id))
                    .filter(list -> !list.isEmpty())
                    .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id))
                    .stream()
                    .flatMap(List::stream)
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getByDistinataire(UUID id) {
        try {
            List<NotificationResponse> responses = Optional.of(repository.findByDestinataireId(id))
                    .filter(list -> !list.isEmpty())
                    .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id))
                    .stream()
                    .flatMap(List::stream)
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<NotificationResponse>> updateNotification(UUID id, NotificationRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(UUID id) {
        try {
            if (!repository.existsByTrackingId(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(LocalDateTime.now(), true, "\"Signalement non trouvé avec l'ID : \" + id", null));
            }
            else {
                repository.delete(repository.findByTrackingId(id).orElseThrow());
                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", null));
            }

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }
}
