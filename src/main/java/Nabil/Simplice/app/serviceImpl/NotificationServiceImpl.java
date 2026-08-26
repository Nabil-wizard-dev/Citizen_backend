package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.NotificationRequest;
import Nabil.Simplice.app.dto.response.NotificationResponse;
import Nabil.Simplice.app.entity.Notification;
import Nabil.Simplice.app.mappers.NotificationMappers;
import Nabil.Simplice.app.repository.NotificationRepository;
import Nabil.Simplice.app.service.NotificationService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository repository;
    private final NotificationMappers mappers;


    public NotificationServiceImpl(NotificationRepository repository, NotificationMappers mappers) {
        this.repository = repository;
        this.mappers = mappers;
    }

    @Override
    public ResponseEntity<ApiResponse<NotificationResponse>> creerNotification(NotificationRequest request) {
        try {
            if (request.getDestinataireId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Le destinataire est obligatoire"));
            }
            if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Le message est obligatoire"));
            }

            Notification saved = repository.save(mappers.toEntity(request));
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(saved), "Notification créée avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la notification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la création de la notification"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAll() {
        try {
            List<NotificationResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(all, "Notifications récupérées avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des notifications", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération des notifications"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<NotificationResponse>> getById(UUID id) {
        try {
            Optional<Notification> notification = repository.findByTrackingId(id);
            if (notification.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Notification non trouvée avec l'ID : " + id));
            }
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(notification.get()), "Notification récupérée avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de la notification {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération de la notification"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getByEntite(UUID id) {
        try {
            // une entite sans notification n'est pas une erreur : on renvoie une liste vide
            List<NotificationResponse> responses = repository.findByEntiteIdOrderByIdDesc(id)
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses, "Notifications récupérées avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des notifications de l'entité {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération des notifications"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getByDistinataire(UUID id) {
        try {
            // boite de reception vide = liste vide, surtout pas une 500 :
            // le front interroge cette route en boucle des la connexion
            List<NotificationResponse> responses = repository.findByDestinataireIdOrderByIdDesc(id)
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses, "Notifications récupérées avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des notifications du destinataire {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération des notifications"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNonLuesByDestinataire(UUID id) {
        try {
            List<NotificationResponse> responses = repository.findByDestinataireIdOrderByIdDesc(id)
                    .stream()
                    .filter(notification -> !notification.isLu())
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses, "Notifications non lues récupérées avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des notifications non lues de {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération des notifications non lues"));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Long>> compterNonLues(UUID destinataireId) {
        try {
            long total = repository.countByDestinataireIdAndLuFalse(destinataireId);
            return ResponseEntity.ok(ApiResponse.success(total, "Compteur récupéré avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors du comptage des notifications non lues de {}", destinataireId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors du comptage des notifications non lues"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<NotificationResponse>> marquerLu(UUID id) {
        try {
            Optional<Notification> existante = repository.findByTrackingId(id);
            if (existante.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Notification non trouvée avec l'ID : " + id));
            }

            Notification notification = existante.get();
            notification.setLu(true);
            Notification lue = repository.save(notification);

            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(lue), "Notification marquée comme lue"));
        } catch (Exception e) {
            logger.error("Erreur lors du marquage de la notification {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors du marquage de la notification"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Integer>> marquerToutesLues(UUID destinataireId) {
        try {
            int total = repository.marquerToutesLues(destinataireId);
            return ResponseEntity.ok(ApiResponse.success(total, "Notifications marquées comme lues"));
        } catch (Exception e) {
            logger.error("Erreur lors du marquage des notifications de {}", destinataireId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors du marquage des notifications"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<NotificationResponse>> updateNotification(UUID id, NotificationRequest request) {
        try {
            Optional<Notification> existante = repository.findByTrackingId(id);
            if (existante.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Notification non trouvée avec l'ID : " + id));
            }

            Notification notification = existante.get();

            // le destinataire et l'entite visee ne changent jamais apres coup :
            // seuls le texte, le type et l'etat de lecture sont modifiables
            if (request.getMessage() != null && !request.getMessage().trim().isEmpty()) {
                notification.setMessage(request.getMessage());
            }
            if (request.getType() != null) {
                notification.setType(request.getType());
            }
            notification.setLu(request.isLu());

            Notification updated = repository.save(notification);
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(updated), "Notification mise à jour avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la notification {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la mise à jour de la notification"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(UUID id) {
        try {
            Optional<Notification> existante = repository.findByTrackingId(id);
            if (existante.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Notification non trouvée avec l'ID : " + id));
            }

            repository.delete(existante.get());
            return ResponseEntity.ok(ApiResponse.success(null, "Notification supprimée avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la notification {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la suppression de la notification"));
        }
    }
}
