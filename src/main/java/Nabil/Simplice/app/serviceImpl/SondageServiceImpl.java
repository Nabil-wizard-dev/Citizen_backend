package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.dto.request.SondageRequest;
import Nabil.Simplice.app.dto.response.SondageResponse;
import Nabil.Simplice.app.entity.Signalement;
import Nabil.Simplice.app.entity.Sondage;
import Nabil.Simplice.app.enums.StatutSignalement;
import Nabil.Simplice.app.enums.StatutSondage;
import Nabil.Simplice.app.mappers.SondageMappers;
import Nabil.Simplice.app.repository.SignalementRepository;
import Nabil.Simplice.app.repository.SondageFeedbackRepository;
import Nabil.Simplice.app.repository.SondageRepository;
import Nabil.Simplice.app.service.SondageService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
public class SondageServiceImpl implements SondageService {

    private static final Logger logger = LoggerFactory.getLogger(SondageServiceImpl.class);

    private final SondageRepository repository;
    private final SondageMappers mappers;
    private final SignalementRepository signalementRepository;
    private final SondageFeedbackRepository feedbackRepository;

    public SondageServiceImpl(SondageRepository repository,
                              SondageMappers mappers,
                              SignalementRepository signalementRepository,
                              SondageFeedbackRepository feedbackRepository) {
        this.repository = repository;
        this.mappers = mappers;
        this.signalementRepository = signalementRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<List<SondageResponse>>> getAllSondages() {
        try {
            List<SondageResponse> all = repository.findAllAvecSignalement()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(all, "Sondages récupérés avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des sondages", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération des sondages"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> getSondageById(UUID id) {
        try {
            Optional<Sondage> sondage = repository.findByTrackingId(id);
            if (sondage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + id));
            }
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(sondage.get()),
                    "Sondage récupéré avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du sondage {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération du sondage"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> getSondageBySignalement(UUID signalementId) {
        try {
            if (!signalementRepository.existsByTrackingId(signalementId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Signalement non trouvé avec l'ID : " + signalementId));
            }

            Optional<Sondage> sondage = repository.findBySignalementTrackingId(signalementId);
            if (sondage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Aucun sondage ouvert pour ce signalement"));
            }
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(sondage.get()),
                    "Sondage récupéré avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du sondage du signalement {}", signalementId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération du sondage"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> addSondage(SondageRequest request) {
        try {
            Optional<Signalement> signalement = signalementRepository.findByTrackingId(request.getSignalementId());
            if (signalement.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Signalement non trouvé avec l'ID : " + request.getSignalementId()));
            }

            // un sondage porte sur un travail acheve : on ne fait pas evaluer un chantier en cours
            StatutSignalement statut = signalement.get().getStatut();
            if (statut != StatutSignalement.TRAITE && statut != StatutSignalement.ARCHIVE) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Ce signalement n'est pas encore traité : aucun sondage ne peut être ouvert"));
            }

            if (repository.existsBySignalementTrackingId(request.getSignalementId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Ce signalement porte déjà un sondage"));
            }

            Sondage saved = repository.save(mappers.toEntity(request, signalement.get()));
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(saved), "Sondage créé avec succès"));
        } catch (DataIntegrityViolationException e) {
            // filet de securite si deux requetes arrivent en meme temps : la contrainte d'unicite tranche
            logger.warn("Tentative de second sondage sur le signalement {}", request.getSignalementId(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("Ce signalement porte déjà un sondage"));
        } catch (Exception e) {
            logger.error("Erreur lors de la création du sondage", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la création du sondage"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> updateSondage(UUID id, SondageRequest request) {
        try {
            Optional<Sondage> existant = repository.findByTrackingId(id);
            if (existant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + id));
            }

            Sondage sondage = existant.get();

            if (sondage.getStatut() == StatutSondage.CLOS) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Ce sondage est clôturé : il ne peut plus être modifié"));
            }

            // seuls le titre et la description sont modifiables :
            // le signalement, les compteurs et les resultats ne se reecrivent pas
            if (request.getTitre() != null) {
                sondage.setTitre(request.getTitre());
            }
            if (request.getDescription() != null) {
                sondage.setDescription(request.getDescription());
            }

            Sondage updated = repository.save(sondage);
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(updated), "Mise à jour réussie"));
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du sondage {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la mise à jour du sondage"));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SondageResponse>> cloturerSondage(UUID id) {
        try {
            Optional<Sondage> existant = repository.findByTrackingId(id);
            if (existant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + id));
            }

            Sondage sondage = existant.get();

            if (sondage.getStatut() == StatutSondage.CLOS) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Ce sondage est déjà clôturé"));
            }

            // le resultat est fige a partir des compteurs : null en cas d'egalite ou d'absence de reponse
            if (sondage.getNbOui() > sondage.getNbNon()) {
                sondage.setResultat(Boolean.TRUE);
            } else if (sondage.getNbNon() > sondage.getNbOui()) {
                sondage.setResultat(Boolean.FALSE);
            } else {
                sondage.setResultat(null);
            }

            sondage.setMoyenneFinale(mappers.moyenne(sondage.getSommeNotes(), sondage.getNbNotes()));
            sondage.setDateCloture(LocalDateTime.now());
            sondage.setStatut(StatutSondage.CLOS);

            Sondage cloture = repository.save(sondage);
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(cloture), "Sondage clôturé avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la clôture du sondage {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la clôture du sondage"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Void>> deleteSondage(UUID id) {
        try {
            Optional<Sondage> existant = repository.findByTrackingId(id);
            if (existant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Sondage non trouvé avec l'ID : " + id));
            }

            // les reponses partent avec le sondage, sinon la contrainte de cle etrangere bloque
            feedbackRepository.deleteBySondageTrackingId(id);
            repository.delete(existant.get());

            return ResponseEntity.ok(ApiResponse.success(null, "Sondage supprimé avec succès"));
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du sondage {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la suppression du sondage"));
        }
    }
}
