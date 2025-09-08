package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.entity.*;
import Nabil.Simplice.app.repository.*;
import Nabil.Simplice.app.service.FichierJoinService;
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
    private final FichierJoinRepository fichierJoinRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final OuvrierRepository ouvrierRepository;
    private final AutoriteRepository autoriteRepository;
    private final FichierJoinService fichierJoinService;

    public SignalementImpl(SignalementRepository repository, SignalementMappers mappers, FichierJoinRepository fichierJoinRepository, UtilisateurRepository utilisateurRepository, OuvrierRepository ouvrierRepository, AutoriteRepository autoriteRepository, FichierJoinService fichierJoinService) {
        this.repository = repository;
        this.mappers = mappers;
        this.fichierJoinRepository = fichierJoinRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.ouvrierRepository = ouvrierRepository;
        this.autoriteRepository = autoriteRepository;
        this.fichierJoinService = fichierJoinService;
    }

    @Override
    public ResponseEntity<ApiResponse<SignalementResponse>> creerSignalement(SignalementRequest request) {
        try {
            Signalement saved = repository.save(mappers.toEntity(request));
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
        }
    }

    // nouvelle methode de creation
    @Override
    public ResponseEntity<ApiResponse<SignalementResponse>> creerSignalementComplet(SignalementRequest request , List<MultipartFile> fichiers) {
        try {

            List<String> fichiersPaths = new ArrayList<>();// pour stocker les chemins

            //mettre en place les fichiers join
            for (MultipartFile fichier : fichiers) {
                ResponseEntity<ApiResponse<String>> jsonResult = fichierJoinService.uploadFichier(fichier);

                if (jsonResult.getBody().getData() != null) {
                    fichiersPaths.add(jsonResult.getBody().getData());
                }else{
                    fichiersPaths = null;
                }
            }
            System.out.println(fichiersPaths);

            // creer le signalement
            request.setFichiersPaths(fichiersPaths); //mettre a jr le champ
            Signalement saved = repository.save(mappers.toEntity(request));

            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", mappers.toResponse(saved)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
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



//    @Override
//    public ResponseEntity<ApiResponse<SignalementResponse>> updateSignalement(UUID id, SignalementRequest request) {
//        try {
//            Signalement signalement = repository.findByTrackingId(id)
//                    .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id));
//            mappers.updateEntityFromRequest(signalement, request);
//            Signalement updated = repository.save(signalement);
//            SignalementResponse response = mappers.toResponse(updated);
//            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", response));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(LocalDateTime.now(), true, "Erreur lors de la recuparation", null));
//        }
//
//
//    }


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

                if (req.getFichiers() != null && !req.getFichiers().isEmpty()) {
                    List<FichierJoin> fichiers = req.getFichiers().stream()
                            .map(id -> fichierJoinRepository.findByTrackingId(id)
                                    .orElseThrow(() -> new RuntimeException("Aucun fichier trouvé pour l’ID : " + id)))
                            .collect(Collectors.toList());
                    existingSignalement.setFichiers(fichiers);
                }

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
    public SignalementResponse updateStatut(Long id, StatutSignalement statut) {
        Signalement signalement = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id));
        
        signalement.setStatut(statut);

        Signalement updated = repository.save(signalement);
        return mappers.toResponse(updated);
    }

    @Override
    public SignalementResponse ajouterCommentaireService(Long id, String commentaire) {
        Signalement signalement = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + id));
        
        signalement.setCommentaireService(commentaire);
        Signalement updated = repository.save(signalement);
        return mappers.toResponse(updated);
    }

    @Override
    public List<SignalementResponse> getSignalementsByTypeService(TypeService typeService) {
        return repository.findAll()
            .stream()
            .filter(s -> s.getTypeService() == typeService)
            .map(mappers::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<SignalementResponse> getSignalementsByStatut(StatutSignalement statut) {
        return repository.findAll()
            .stream()
            .filter(s -> s.getStatut() == statut)
            .map(mappers::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<SignalementResponse> getSignalementsByPriorite(Integer priorite) {
        return repository.findAll()
            .stream()
            .filter(s -> s.getPriorite() != null && s.getPriorite().equals(priorite))
            .map(mappers::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<SignalementResponse> getSignalementsByServiceId(Long serviceId) {
        return repository.findAll()
            .stream()
            .filter(s -> s.getServiceId().equals(serviceId))
            .map(mappers::toResponse)
            .collect(Collectors.toList());
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

//    @Override
//    public SignalementResponse creerSignalementAvecImage(SignalementRequest request, MultipartFile image) {
//        Signalement entity = mappers.toEntity(request);
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        Utilisateur createur = utilisateurRepository.findByEmail(email)
//            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
//        entity.setCreateur(createur);
//        System.out.println("Avant save (image) : " + entity);
//        entity = repository.save(entity);
//        System.out.println("Après save (image) : " + entity);
//        // Sauvegarde de l'image et association au signalement
//        if (image != null && !image.isEmpty()) {
//            FichierJoin fichier = new FichierJoin();
//            fichier.setNom(image.getOriginalFilename());
//            fichier.setTaille(image.getSize());
//            fichier.setSignalement(entity);
//            fichier.setDateCreation(java.time.LocalDateTime.now());
//            fichier.setTrackingId(java.util.UUID.randomUUID());
//            fichier.setCode("IMG-" + System.currentTimeMillis());
//            // Sauvegarde physique
//            try {
//                java.nio.file.Path directory = java.nio.file.Paths.get("Media", "images");
//                java.nio.file.Files.createDirectories(directory);
//                String newFilename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//                java.nio.file.Path targetPath = directory.resolve(newFilename);
//                image.transferTo(targetPath);
//                fichier.setUrl(targetPath.toString());
//                // Extension
//                String ext = image.getOriginalFilename().toLowerCase();
//                if (ext.endsWith(".jpg")) fichier.setExtension(Nabil.Simplice.app.enums.ExtensionFichier.JPG);
//                else if (ext.endsWith(".jpeg")) fichier.setExtension(Nabil.Simplice.app.enums.ExtensionFichier.JPEG);
//                else if (ext.endsWith(".png")) fichier.setExtension(Nabil.Simplice.app.enums.ExtensionFichier.PNG);
//                else if (ext.endsWith(".gif")) fichier.setExtension(Nabil.Simplice.app.enums.ExtensionFichier.GIF);
//                else throw new IllegalArgumentException("Extension de fichier non supportée");
//            } catch (Exception e) {
//                throw new RuntimeException("Erreur lors de l'upload de l'image : " + e.getMessage(), e);
//            }
//            fichierJoinRepository.save(fichier);
//            if (entity.getFichiers() == null) entity.setFichiers(new ArrayList<>());
//            entity.getFichiers().add(fichier);
//            repository.save(entity);
//        }
//        return mappers.toResponse(entity);
//    }

    @Override
    public ResponseEntity<ApiResponse<String>> uploadDevis(MultipartFile pdf, String titre, String signalementId, String ouvrierId, String dateCreation) {
        try {
            System.out.println("=== UPLOAD DEVIS PDF ===");
            System.out.println("Titre: " + titre);
            System.out.println("Signalement ID: " + signalementId);
            System.out.println("Ouvrier ID: " + ouvrierId);
            System.out.println("Date création: " + dateCreation);
            System.out.println("Taille PDF: " + pdf.getSize() + " bytes");

            // Utiliser le service FichierJoin pour uploader le PDF
            ResponseEntity<ApiResponse<String>> uploadResult = fichierJoinService.uploadFichier(pdf);
            
            if (uploadResult.getBody() != null && uploadResult.getBody().getData() != null) {
                String filePath = uploadResult.getBody().getData();
                System.out.println("PDF uploadé avec succès: " + filePath);
                
                // Chercher le fichier dans la base pour récupérer son trackingId
                // Le service retourne le chemin avec / mais sauvegarde avec \
                String cheminRecherche = filePath.replace("/", "\\");
                System.out.println("Chemin retourné par le service: " + filePath);
                System.out.println("Chemin pour la recherche: " + cheminRecherche);
                java.util.Optional<FichierJoin> fichierOpt = fichierJoinRepository.findByChemin(cheminRecherche);
                if (fichierOpt.isPresent()) {
                    FichierJoin fichier = fichierOpt.get();
                    String trackingId = fichier.getTrackingId().toString();
                    System.out.println("TrackingId du fichier: " + trackingId);
                    
                    // Chercher le signalement et associer le fichier
                    try {
                        java.util.Optional<Signalement> signalementOpt = repository.findByTrackingId(java.util.UUID.fromString(signalementId));
                        if (signalementOpt.isPresent()) {
                            Signalement signalement = signalementOpt.get();
                            fichier.setSignalement(signalement);
                            fichierJoinRepository.save(fichier);
                            System.out.println("Fichier associé au signalement: " + signalement.getId());
                        } else {
                            System.out.println("Signalement non trouvé: " + signalementId);
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'association au signalement: " + e.getMessage());
                    }
                    
                    // Retourner le trackingId du fichier
                    return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Devis uploadé avec succès", trackingId));
                } else {
                    System.out.println("Fichier non trouvé dans la base après upload");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(LocalDateTime.now(), false, "Fichier non trouvé dans la base", null));
                }
            } else {
                System.out.println("Erreur lors de l'upload du PDF");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'upload du PDF", null));
            }
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
            
            // Récupérer tous les fichiers PDF associés à ce signalement
            List<FichierJoin> fichiers = fichierJoinRepository.findBySignalementId(signalement.getId());
            
            // Filtrer seulement les PDFs et créer des objets DevisResponse
            List<DevisResponse> devisList = fichiers.stream()
                .filter(fichier -> fichier.getExtension() != null && fichier.getExtension().toLowerCase().contains(".pdf"))
                .map(fichier -> {
                    // Utiliser l'ouvrier du signalement s'il existe, sinon une valeur par défaut
                    String ouvrierId = signalement.getOuvrier() != null ? 
                        signalement.getOuvrier().getTrackingId().toString() : "Non assigné";
                    
                    return new DevisResponse(
                        fichier.getTrackingId().toString(),
                        "Devis - " + signalement.getTitre(),
                        signalement.getTrackingId().toString(),
                        ouvrierId,
                        fichier.getCreateDate().toString(),
                        fichier.getChemin()
                    );
                })
                .collect(java.util.stream.Collectors.toList());
            
            System.out.println("Nombre de PDFs trouvés: " + devisList.size());
            
            return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "succes", devisList));
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
            
            // Chercher le fichier dans la base de données via FichierJoin
            java.util.Optional<FichierJoin> fichierOpt = fichierJoinRepository.findByTrackingId(java.util.UUID.fromString(devisId));
            
            if (fichierOpt.isEmpty()) {
                System.out.println("Fichier non trouvé dans la base de données");
                return ResponseEntity.notFound().build();
            }
            
            FichierJoin fichier = fichierOpt.get();
            String filePath = fichier.getChemin();
            
            System.out.println("Chemin du fichier: " + filePath);
            
            java.io.File file = new java.io.File(filePath);
            if (!file.exists()) {
                System.out.println("Fichier physique non trouvé: " + filePath);
                return ResponseEntity.notFound().build();
            }

            // Lire le fichier
            byte[] pdfBytes = java.nio.file.Files.readAllBytes(file.toPath());
            
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
            
            // Chercher le fichier
            java.util.Optional<FichierJoin> fichierOpt = fichierJoinRepository.findByTrackingId(java.util.UUID.fromString(fichierId));
            if (fichierOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Fichier non trouvé", null));
            }
            
            Signalement signalement = signalementOpt.get();
            FichierJoin fichier = fichierOpt.get();
            
            // Associer le fichier au signalement
            fichier.setSignalement(signalement);
            fichierJoinRepository.save(fichier);
            
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
            
            // Sauvegarder le PDF via FichierJoinService
            ResponseEntity<ApiResponse<String>> uploadResult = fichierJoinService.uploadFichier(pdf);
            
            if (uploadResult.getBody() != null && uploadResult.getBody().getData() != null) {
                String filePath = uploadResult.getBody().getData();
                System.out.println("PDF uploadé avec succès: " + filePath);
                
                // Convertir le chemin pour la recherche (comme dans uploadDevis)
                String cheminRecherche = filePath.replace("/", "\\");
                System.out.println("Chemin pour la recherche: " + cheminRecherche);
                
                // Récupérer le FichierJoin créé
                java.util.Optional<FichierJoin> fichierOpt = fichierJoinRepository.findByChemin(cheminRecherche);
                
                if (fichierOpt.isPresent()) {
                    FichierJoin fichier = fichierOpt.get();
                    String trackingId = fichier.getTrackingId().toString();
                    
                    // Associer le fichier au signalement
                    fichier.setSignalement(signalement);
                    fichierJoinRepository.save(fichier);
                    
                    // Mettre à jour le commentaire du signalement si fourni
                    if (commentaire != null && !commentaire.trim().isEmpty()) {
                        signalement.setCommentaireService(commentaire);
                        repository.save(signalement);
                    }
                    
                    System.out.println("Rapport d'avancement associé avec succès au signalement");
                    System.out.println("TrackingId du fichier: " + trackingId);
                    
                    return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Rapport d'avancement uploadé avec succès", trackingId));
                } else {
                    System.out.println("FichierJoin non trouvé après upload");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'association du fichier", null));
                }
            } else {
                System.out.println("Échec de l'upload du fichier");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'upload du fichier", null));
            }
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
            
            // Récupérer tous les fichiers PDF associés à ce signalement
            List<FichierJoin> fichiers = fichierJoinRepository.findBySignalementId(signalement.getId());
            
            // Filtrer seulement les PDFs de rapports (pas les devis)
            List<DevisResponse> rapportsList = fichiers.stream()
                .filter(fichier -> fichier.getExtension() != null && fichier.getExtension().toLowerCase().contains(".pdf"))
                .filter(fichier -> {
                    // Exclure les devis en utilisant le code ou le chemin
                    String code = fichier.getCode() != null ? fichier.getCode().toLowerCase() : "";
                    String chemin = fichier.getChemin() != null ? fichier.getChemin().toLowerCase() : "";
                    return !code.contains("devis") && !chemin.contains("devis");
                })
                .map(fichier -> {
                    // Utiliser l'ouvrier du signalement s'il existe, sinon une valeur par défaut
                    String ouvrierId = signalement.getOuvrier() != null ? 
                        signalement.getOuvrier().getTrackingId().toString() : "Non assigné";
                    
                    return new DevisResponse(
                        fichier.getTrackingId().toString(),
                        "Rapport d'avancement - " + signalement.getTitre(),
                        signalement.getTrackingId().toString(),
                        ouvrierId,
                        fichier.getCreateDate().toString(),
                        fichier.getChemin()
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
