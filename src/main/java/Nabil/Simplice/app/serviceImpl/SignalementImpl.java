package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.entity.*;
import Nabil.Simplice.app.repository.*;
import Nabil.Simplice.app.service.FileStorageService;
import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Nabil.Simplice.app.service.SignalementService;
import Nabil.Simplice.app.mappers.SignalementMappers;
import Nabil.Simplice.app.dto.request.SignalementRequest;
import Nabil.Simplice.app.dto.response.SignalementResponse;
import Nabil.Simplice.app.dto.response.DevisResponse;
import Nabil.Simplice.app.enums.StatutSignalement;
import Nabil.Simplice.app.enums.TypeService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
public class SignalementImpl implements SignalementService {
    
    private final SignalementRepository repository;
    private final SignalementMappers mappers;
    private final UtilisateurRepository utilisateurRepository;
    private final OuvrierRepository ouvrierRepository;
    private final AutoriteRepository autoriteRepository;
    private final FileStorageService fileStorageService;
    private final DevisRepository devisRepository;
    private final RapportRepository rapportRepository;

    public SignalementImpl(SignalementRepository repository, SignalementMappers mappers, UtilisateurRepository utilisateurRepository, OuvrierRepository ouvrierRepository, AutoriteRepository autoriteRepository, FileStorageService fileStorageService, DevisRepository devisRepository, RapportRepository rapportRepository) {
        this.repository = repository;
        this.mappers = mappers;
        this.utilisateurRepository = utilisateurRepository;
        this.ouvrierRepository = ouvrierRepository;
        this.autoriteRepository = autoriteRepository;
        this.fileStorageService = fileStorageService;
        this.devisRepository = devisRepository;
        this.rapportRepository = rapportRepository;
    }


    @Override
    public ResponseEntity<ApiResponse<SignalementResponse>> creerSignalement(SignalementRequest request, List<MultipartFile> fichiers) {
        try {
            // Créer le signalement d'abord
            Signalement signalement = mappers.toEntity(request);
            Signalement saved = repository.save(signalement);

            // Gérer les fichiers s'il y en a
            if (fichiers != null && !fichiers.isEmpty()) {
                List<String> filePaths = fileStorageService.storeFiles(fichiers);
                saved.setFichiersPaths(filePaths);
                saved = repository.save(saved);
            }
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Signalement créé avec succès", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la création: " + e.getMessage(), null));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getAll() {
        try {
            List<SignalementResponse> all = repository.findAll()
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getAllByOuvrier(UUID id) {
        try {
            List<SignalementResponse> all = repository.findByOuvrierTrackingId(id)
                    .stream()
                    .map(mappers::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", all));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SignalementResponse>> getById(UUID id) {
        try {
            SignalementResponse Response = repository.findByTrackingId(id)
                    .map(mappers::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id));
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", Response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }





    public ResponseEntity<ApiResponse<SignalementResponse>> updateSignalement(UUID trackingId, SignalementRequest req) {
        try {
            if (!repository.existsByTrackingId(trackingId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(LocalDateTime.now(), true, "Signalement introuvable", null));
            } else {

                Signalement existingSignalement = repository.findByTrackingId(trackingId).get();

                if (req.getTitre() != null) {
                    existingSignalement.setTitre(req.getTitre());
                }

                if (req.getCode() != null) {
                    existingSignalement.setCode(req.getCode());
                }

                if (req.getDescription() != null) {
                    existingSignalement.setDescription(req.getDescription());
                }

                if (req.getTypeService() != null) {
                    existingSignalement.setTypeService(req.getTypeService());
                }

                if (req.getServiceId() != null) {
                    existingSignalement.setServiceId(req.getServiceId());
                }

                // Note: La gestion des fichiers se fait maintenant séparément via MultipartFile dans les endpoints

                if (req.getCommentaireService() != null) {
                    existingSignalement.setCommentaireService(req.getCommentaireService());
                }

                if (req.getPriorite() != null) {
                    existingSignalement.setPriorite(req.getPriorite());
                }

                if (req.getLatitude() != null) {
                    try {
//                        existingSignalement.setLatitude(Double.parseDouble(req.getLatitude()));
                        existingSignalement.setLatitude(req.getLatitude());
                    } catch (NumberFormatException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(LocalDateTime.now(), true, "Latitude invalide", null));
                    }
                }

                if (req.getLongitude() != null) {
                    try {
//                        existingSignalement.setLongitude(Double.parseDouble(req.getLongitude()));
                        existingSignalement.setLongitude(req.getLongitude());
                    } catch (NumberFormatException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(LocalDateTime.now(), true, "Longitude invalide", null));
                    }
                }

                if (req.getOuvrierUuid() != null) {
                    Ouvrier ouvrier = ouvrierRepository.findByTrackingId(req.getOuvrierUuid())
                            .orElseThrow(() -> new RuntimeException("Ouvrier introuvable pour l'UUID : " + req.getOuvrierUuid()));
                    existingSignalement.setOuvrier(ouvrier);
                }

                if (req.getTraiteurUuid() != null) {
                    AutoriteLocale traiteur = autoriteRepository.findByTrackingId(req.getTraiteurUuid())
                            .orElseThrow(() -> new RuntimeException("Traiteur introuvable pour l'UUID : " + req.getTraiteurUuid()));
                    existingSignalement.setUtilisateurTraiteur(traiteur);
                }

                Signalement updatedSignalement = repository.save(existingSignalement);
                SignalementResponse response = mappers.toResponse(updatedSignalement);

                return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Mise à jour réussie", response));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la mise à jour du signalement.", null));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<Void>> delete(UUID id) {
        try {
            if (!repository.existsByTrackingId(id)) {
//                throw new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id);
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

    @Override
    public ResponseEntity<ApiResponse<SignalementResponse>> updateStatut(UUID id, StatutSignalement statut) {
        try {
            Signalement signalement = repository.findByTrackingId(id)
                .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id));
            
            signalement.setStatut(statut);
            Signalement updated = repository.save(signalement);
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Statut mis à jour avec succès", mappers.toResponse(updated)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la mise à jour du statut: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SignalementResponse>> ajouterCommentaireService(UUID id, String commentaire) {
        try {
            Signalement signalement = repository.findByTrackingId(id)
                .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id));
            
            signalement.setCommentaireService(commentaire);
            Signalement updated = repository.save(signalement);
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Commentaire ajouté avec succès", mappers.toResponse(updated)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'ajout du commentaire: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByTypeService(TypeService typeService) {
        try {
            List<SignalementResponse> signalements = repository.findAll()
                .stream()
                .filter(s -> s.getTypeService() == typeService)
                .map(mappers::toResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Signalements récupérés avec succès", signalements));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la récupération: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByStatut(StatutSignalement statut) {
        try {
            List<SignalementResponse> signalements = repository.findAll()
                .stream()
                .filter(s -> s.getStatut() == statut)
                .map(mappers::toResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Signalements récupérés avec succès", signalements));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la récupération: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByPriorite(Integer priorite) {
        try {
            List<SignalementResponse> signalements = repository.findAll()
                .stream()
                .filter(s -> s.getPriorite() != null && s.getPriorite().equals(priorite))
                .map(mappers::toResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Signalements récupérés avec succès", signalements));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la récupération: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<SignalementResponse>>> getSignalementsByServiceId(Long serviceId) {
        try {
            List<SignalementResponse> signalements = repository.findAll()
                .stream()
                .filter(s -> s.getServiceId().equals(serviceId))
                .map(mappers::toResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Signalements récupérés avec succès", signalements));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la récupération: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<SignalementResponse>> finaliserSignalement(String trackingId) {
        try {
            // Vérifier si le trackingId est valide
            if (trackingId == null || trackingId.trim().isEmpty()) {
                return ResponseEntity.status(400).body(ApiResponse.error("Le trackingId ne peut pas être vide"));
            }
            
            // Convertir le trackingId en UUID
            UUID uuid;
            try {
                uuid = UUID.fromString(trackingId);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).body(ApiResponse.error("Format de trackingId invalide"));
            }
            
            // Chercher le signalement
            Optional<Signalement> signalementOpt = repository.findByTrackingId(uuid);
            if (signalementOpt.isEmpty()) {
                return ResponseEntity.status(404).body(ApiResponse.error("Signalement non trouvé"));
            }
            
            Signalement signalement = signalementOpt.get();
            
            // Vérifier que le signalement est en cours de traitement
            if (signalement.getStatut() != StatutSignalement.EN_COURS) {
                String message;
                switch (signalement.getStatut()) {
                    case StatutSignalement.EN_ATTENTE:
                        message = "Le signalement doit d'abord être activé avant d'être finalisé";
                        break;
                    case StatutSignalement.TRAITE:
                        message = "Le signalement est déjà traité";
                        break;
                    case StatutSignalement.REJETE:
                        message = "Un signalement rejeté ne peut pas être finalisé";
                        break;
                    case StatutSignalement.ARCHIVE:
                        message = "Un signalement archivé ne peut pas être finalisé";
                        break;
                    default:
                        message = "Statut invalide pour la finalisation";
                }
                return ResponseEntity.status(400).body(ApiResponse.error(message));
            }
            
            // Finaliser le signalement (changer vers TRAITE)
            signalement.setStatut(StatutSignalement.TRAITE);
            Signalement updated = repository.save(signalement);
            
            return ResponseEntity.ok(ApiResponse.success(mappers.toResponse(updated), "Signalement finalisé avec succès"));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(ApiResponse.error("Erreur lors de la finalisation du signalement: " + e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<ApiResponse<String>> uploadDevis(MultipartFile pdf, String titre, String signalementId, String ouvrierId, String dateCreation) {
        try {
            System.out.println("=== UPLOAD DEVIS PDF ===");
            System.out.println("Titre: " + titre);
            System.out.println("Signalement ID: " + signalementId);
            System.out.println("Ouvrier ID: " + ouvrierId);
            System.out.println("Date création: " + dateCreation);
            System.out.println("Taille PDF: " + pdf.getSize() + " bytes");

            // Stocker le fichier PDF via le nouveau service
            String filePath = fileStorageService.storeFile(pdf);
            System.out.println("PDF uploadé avec succès: " + filePath);
            
            // Créer l'entité Devis
            Devis devis = new Devis(titre, filePath, dateCreation);
            
            // Chercher le signalement
            Optional<Signalement> signalementOpt = repository.findByTrackingId(UUID.fromString(signalementId));
            if (signalementOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Signalement non trouvé", null));
            }
            
            devis.setSignalement(signalementOpt.get());
            
            // Chercher l'ouvrier si fourni
            if (ouvrierId != null && !ouvrierId.isEmpty()) {
                Optional<Ouvrier> ouvrierOpt = ouvrierRepository.findByTrackingId(UUID.fromString(ouvrierId));
                ouvrierOpt.ifPresent(devis::setOuvrier);
            }
            
            // Sauvegarder le devis
            Devis savedDevis = devisRepository.save(devis);
            
            System.out.println("Devis créé avec succès. TrackingId: " + savedDevis.getTrackingId());
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, 
                "Devis uploadé avec succès", savedDevis.getTrackingId().toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'upload du devis: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<DevisResponse>>> getDevisBySignalement(String signalementId) {
        try {
            System.out.println("=== RÉCUPÉRATION DEVIS PAR SIGNALEMENT ===");
            System.out.println("Signalement ID: " + signalementId);
            
            // Chercher le signalement
            java.util.Optional<Signalement> signalementOpt = repository.findByTrackingId(java.util.UUID.fromString(signalementId));
            
            if (signalementOpt.isEmpty()) {
                System.out.println("Signalement non trouvé");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Signalement non trouvé", null));
            }
            
            Signalement signalement = signalementOpt.get();
            
            // Récupérer tous les devis associés à ce signalement
            List<Devis> devisList = devisRepository.findBySignalementId(signalement.getId());
            
            // Convertir en DevisResponse
            List<DevisResponse> devisResponseList = devisList.stream()
                .map(devis -> {
                    String ouvrierId = devis.getOuvrier() != null ? 
                        devis.getOuvrier().getTrackingId().toString() : "Non assigné";
                    
                    return new DevisResponse(
                        devis.getTrackingId().toString(),
                        devis.getTitre(),
                        signalement.getTrackingId().toString(),
                        ouvrierId,
                        devis.getDateCreationDevis(),
                        devis.getFilePath()
                    );
                })
                .collect(Collectors.toList());
            
            System.out.println("Nombre de devis trouvés: " + devisResponseList.size());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", devisResponseList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la récupération des devis: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadDevisPdf(String devisId) {
        try {
            System.out.println("=== TÉLÉCHARGEMENT DEVIS PDF ===");
            System.out.println("Devis ID: " + devisId);
            
            // Chercher le devis dans la base de données
            Optional<Devis> devisOpt = devisRepository.findByTrackingId(UUID.fromString(devisId));
            
            if (devisOpt.isEmpty()) {
                System.out.println("Devis non trouvé dans la base de données");
                return ResponseEntity.notFound().build();
            }
            
            Devis devis = devisOpt.get();
            String filePath = devis.getFilePath();
            
            System.out.println("Chemin du fichier: " + filePath);
            
            // Lire le fichier via le service de stockage
            byte[] pdfBytes = fileStorageService.getFile(filePath);
            
            System.out.println("Fichier lu avec succès, taille: " + pdfBytes.length + " bytes");
            
            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"devis.pdf\"")
                .header("Content-Type", "application/pdf")
                .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> associerDevis(String signalementId, String fichierId) {
        try {
            System.out.println("=== ASSOCIATION DEVIS ===");
            System.out.println("Signalement ID: " + signalementId);
            System.out.println("Fichier ID: " + fichierId);
            
            // Chercher le signalement
            java.util.Optional<Signalement> signalementOpt = repository.findByTrackingId(java.util.UUID.fromString(signalementId));
            if (signalementOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Signalement non trouvé", null));
            }
            
            // Chercher le devis
            Optional<Devis> devisOpt = devisRepository.findByTrackingId(UUID.fromString(fichierId));
            if (devisOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Devis non trouvé", null));
            }
            
            Signalement signalement = signalementOpt.get();
            Devis devis = devisOpt.get();
            
            // Associer le devis au signalement (si pas déjà fait)
            devis.setSignalement(signalement);
            devisRepository.save(devis);
            
            System.out.println("Devis associé avec succès au signalement");
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Devis associé avec succès", fichierId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'association: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> activerSignalement(UUID id) {
        Signalement s = repository.findByTrackingId(id).orElseThrow();
        s.setStatut(StatutSignalement.EN_COURS);
        repository.save(s);
        return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Signalement activé", null));
    }

    @Override
    public ResponseEntity<ApiResponse<String>> rejeterSignalement(UUID id) {
        Signalement s = repository.findByTrackingId(id).orElseThrow();
        s.setStatut(StatutSignalement.REJETE);
        repository.save(s);
        return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), false, "Signalement rejeté", null));
    }

    @Override
    public ResponseEntity<ApiResponse<String>> ajouterRapportAvancement(UUID id, MultipartFile pdf, String commentaire) {
        try {
            System.out.println("=== AJOUT RAPPORT AVANCEMENT ===");
            System.out.println("Signalement ID: " + id);
            System.out.println("Commentaire: " + commentaire);
            System.out.println("Fichier: " + pdf.getOriginalFilename());
            
            // Vérifier que le signalement existe
            java.util.Optional<Signalement> signalementOpt = repository.findByTrackingId(id);
            if (signalementOpt.isEmpty()) {
                System.out.println("Signalement non trouvé");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Signalement non trouvé", null));
            }
            
            Signalement signalement = signalementOpt.get();
            
            // Stocker le PDF via le nouveau service de stockage
            String filePath = fileStorageService.storeFile(pdf);
            System.out.println("PDF uploadé avec succès: " + filePath);
            
            // Créer l'entité Rapport
            Rapport rapport = new Rapport(commentaire, filePath);
            rapport.setSignalement(signalement);
            
            // Sauvegarder le rapport
            Rapport savedRapport = rapportRepository.save(rapport);
            
            // Mettre à jour le commentaire du signalement si fourni
            if (commentaire != null && !commentaire.trim().isEmpty()) {
                signalement.setCommentaireService(commentaire);
                repository.save(signalement);
            }
            
            System.out.println("Rapport d'avancement créé avec succès");
            System.out.println("TrackingId du rapport: " + savedRapport.getTrackingId());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, 
                "Rapport d'avancement uploadé avec succès", savedRapport.getTrackingId().toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'ajout du rapport: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<DevisResponse>>> getRapportsBySignalement(String signalementId) {
        try {
            System.out.println("=== RÉCUPÉRATION RAPPORTS PAR SIGNALEMENT ===");
            System.out.println("Signalement ID: " + signalementId);
            
            // Chercher le signalement
            java.util.Optional<Signalement> signalementOpt = repository.findByTrackingId(java.util.UUID.fromString(signalementId));
            
            if (signalementOpt.isEmpty()) {
                System.out.println("Signalement non trouvé");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Signalement non trouvé", null));
            }
            
            Signalement signalement = signalementOpt.get();
            
            // Récupérer tous les rapports associés à ce signalement
            List<Rapport> rapports = rapportRepository.findBySignalementId(signalement.getId());
            
            // Convertir en DevisResponse (réutilisation de la structure pour les rapports)
            List<DevisResponse> rapportsList = rapports.stream()
                .map(rapport -> {
                    // Utiliser l'ouvrier du signalement s'il existe, sinon une valeur par défaut
                    String ouvrierId = signalement.getOuvrier() != null ? 
                        signalement.getOuvrier().getTrackingId().toString() : "Non assigné";
                    
                    return new DevisResponse(
                        rapport.getTrackingId().toString(),
                        "Rapport d'avancement - " + signalement.getTitre(),
                        signalement.getTrackingId().toString(),
                        ouvrierId,
                        rapport.getCreateDate().toString(),
                        rapport.getFilePath()
                    );
                })
                .collect(java.util.stream.Collectors.toList());
            
            System.out.println("Nombre de rapports trouvés: " + rapportsList.size());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", rapportsList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de la récupération des rapports: " + e.getMessage(), null));
        }
    }
}
