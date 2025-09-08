package Nabil.Simplice.app.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.UUID;

import Nabil.Simplice.app.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import Nabil.Simplice.app.entity.FichierJoin;
import Nabil.Simplice.app.mappers.FichierJoinMappers;
import Nabil.Simplice.app.repository.FichierJoinRepository;
import Nabil.Simplice.app.repository.SignalementRepository;
import Nabil.Simplice.app.service.FichierJoinService;

@Service
@Transactional
public class FichierJoinServiceImpl implements FichierJoinService {

    private final FichierJoinRepository fichierRepository;
    private final SignalementRepository signalementRepository;
    private final FichierJoinMappers fichierMappers;

    public FichierJoinServiceImpl(
            FichierJoinRepository fichierRepository,
            SignalementRepository signalementRepository,
            FichierJoinMappers fichierMappers) {
        this.fichierRepository = fichierRepository;
        this.signalementRepository = signalementRepository;
        this.fichierMappers = fichierMappers;
    }

//    @Override
//    public Optional<FichierJoinResponse> getFichierById(Long id) {
//        return fichierRepository.findById(id)
//            .map(fichierMappers::toResponse);
//    }
//
//    @Override
//    public List<FichierJoinResponse> getFichiersBySignalement(Long signalementId) {
//        return fichierRepository.findBySignalementId(signalementId)
//            .stream()
//            .map(fichierMappers::toResponse)
//            .collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteFichier(Long id) {
//        if (!fichierRepository.existsById(id)) {
//            throw new EntityNotFoundException("Fichier non trouvé avec l'ID : " + id);
//        }
//        fichierRepository.deleteById(id);
//    }

//    @Override
//    public FichierJoinResponse saveFichier(MultipartFile fichier, Long signalementId) {
//        String originalFilename = fichier.getOriginalFilename();
//        if (originalFilename == null) {
//            throw new IllegalArgumentException("Nom de fichier invalide");
//        }
//
//        // Vérifier si le signalement existe
//        Signalement signalement = signalementRepository.findById(signalementId)
//            .orElseThrow(() -> new EntityNotFoundException("Signalement non trouvé avec l'ID : " + signalementId));
//
//        String lowerCaseName = originalFilename.toLowerCase();
//        if (!lowerCaseName.endsWith(".jpg") && !lowerCaseName.endsWith(".jpeg") &&
//            !lowerCaseName.endsWith(".png") && !lowerCaseName.endsWith(".gif")) {
//            throw new IllegalArgumentException("Seules les images sont acceptées (jpg, jpeg, png, gif)");
//        }
//
//        try {
//            long identifiantTime = System.currentTimeMillis();
//            String newFilename = identifiantTime + "_" + originalFilename;
//
//            // Sauvegarde du fichier
//            Path targetPath = saveFile(fichier, "images", newFilename);
//
//            // Création de l'entité FichierJoin
//            FichierJoin fichierJoin = new FichierJoin();
//            fichierJoin.setTrackingId(UUID.randomUUID());
//            fichierJoin.setCode("IMG-" + identifiantTime);
//            fichierJoin.setNom(originalFilename);
//            fichierJoin.setTaille(fichier.getSize());
//            fichierJoin.setUrl(targetPath.toString());
//            fichierJoin.setExtension(getExtensionFromFilename(originalFilename));
//            fichierJoin.setDateCreation(LocalDateTime.now());
//            fichierJoin.setSignalement(signalement);
//
//            FichierJoin savedFichier = fichierRepository.save(fichierJoin);
//            return fichierMappers.toResponse(savedFichier);
//
//        } catch (IOException e) {
//            throw new RuntimeException("Erreur lors de l'upload de l'image : " + e.getMessage(), e);
//        }
//    }

//    private Path saveFile(MultipartFile file, String subDirectory, String filename) throws IOException {
//        Path directory = Paths.get("Media", subDirectory);
//        Files.createDirectories(directory);
//        Path targetPath = directory.resolve(filename);
//        file.transferTo(targetPath);
//        return targetPath;
//    }
//
//    private ExtensionFichier getExtensionFromFilename(String filename) {
//        String ext = filename.toLowerCase();
//        if (ext.endsWith(".jpg")) return ExtensionFichier.JPG;
//        if (ext.endsWith(".jpeg")) return ExtensionFichier.JPEG;
//        if (ext.endsWith(".png")) return ExtensionFichier.PNG;
//        if (ext.endsWith(".gif")) return ExtensionFichier.GIF;
//        throw new IllegalArgumentException("Extension de fichier non supportée");
//    }

    //pour l'upload de fichier de tous type
    @Override
    public ResponseEntity<ApiResponse<String>> uploadFichier(MultipartFile fichier) {
        String originalFilename = fichier.getOriginalFilename();
        long identifiantTime = System.currentTimeMillis(); //pour renommer le fichier

        if (originalFilename == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(LocalDateTime.now(), false, "Nom de fichier invalide", null));
        }
        else {
            originalFilename = identifiantTime+fichier.getOriginalFilename();
            String lowerCaseName = originalFilename.toLowerCase();

            try {
                String extension = lowerCaseName.substring(lowerCaseName.lastIndexOf("."));
                String taille = fichier.getSize() / 1024 + " Ko"; // taille en Ko
                UUID trackingId = UUID.randomUUID();

                String code = "FILE-" + identifiantTime; // ou autre logique

                Path targetPath;
                Duration duree = null; // par défaut

                if (!lowerCaseName.endsWith(".pdf")) {

                    if (lowerCaseName.endsWith(".mp4") || lowerCaseName.endsWith(".avi") || lowerCaseName.endsWith(".mov") || lowerCaseName.endsWith(".mkv")) {
                        Path videoDir = Paths.get("Media/videos");
                        Files.createDirectories(videoDir);
                        targetPath = videoDir.resolve(originalFilename);
                        fichier.transferTo(targetPath);

                        // Calcul de durée (à faire si tu as une lib de lecture de média comme Xuggler, JCodec, etc.)
                        // duree = Duration.ofMinutes(2); // Exemple temporaire

                        fichierRepository.save(new FichierJoin(trackingId, code, taille, targetPath.toString(), duree, extension));//	                saveFichier(trackingId, code, taille, targetPath.toString(), duree, extension);
                        return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Fichier vidéo uploadé", targetPath.toString().replace("\\", "/")));
                    }
                    else if (lowerCaseName.endsWith(".mp3") || lowerCaseName.endsWith(".wav") || lowerCaseName.endsWith(".aac")) {
                        Path audioDir = Paths.get("Media/audios");
                        Files.createDirectories(audioDir);
                        targetPath = audioDir.resolve(originalFilename);
                        fichier.transferTo(targetPath);

                        // duree = Duration.ofMinutes(3); // Exemple temporaire
                        fichierRepository.save(new FichierJoin(trackingId, code, taille, targetPath.toString(), duree, extension));//	                saveFichier(trackingId, code, taille, targetPath.toString(), duree, extension);
                        return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Fichier audio uploadé", targetPath.toString().replace("\\", "/")));
                    }
                    else if (lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg") || lowerCaseName.endsWith(".png") || lowerCaseName.endsWith(".gif")) {
                        Path imageDir = Paths.get("Media/images");
                        Files.createDirectories(imageDir);
                        targetPath = imageDir.resolve(originalFilename);
                        fichier.transferTo(targetPath);

                        fichierRepository.save(new FichierJoin(trackingId, code, taille, targetPath.toString(), null, extension));

                        return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Fichier image uploadé", targetPath.toString().replace("\\", "/")));
                    }
                    else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(LocalDateTime.now(), false, "Type de fichier non pris en charge", null));
                    }
                } else {
                    Path pdfDir = Paths.get("Media/pdfs");
                    Files.createDirectories(pdfDir);
                    targetPath = pdfDir.resolve(originalFilename);
                    fichier.transferTo(targetPath);


                    fichierRepository.save(new FichierJoin(trackingId, code, taille, targetPath.toString(), null, extension));

                    return ResponseEntity.ok(new ApiResponse<>(LocalDateTime.now(), true, "Fichier PDF uploadé", targetPath.toString().replace("\\", "/")));
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(LocalDateTime.now(), false, "Erreur lors de l'upload du fichier", null));
            }
        }
    }
}