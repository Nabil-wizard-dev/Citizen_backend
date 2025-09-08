package Nabil.Simplice.app.serviceImpl;

import Nabil.Simplice.app.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String UPLOAD_DIR = "Media";
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of(".jpg", ".jpeg", ".png", ".gif");
    private static final List<String> ALLOWED_VIDEO_TYPES = List.of(".mp4", ".avi", ".mov", ".mkv");
    private static final List<String> ALLOWED_AUDIO_TYPES = List.of(".mp3", ".wav", ".aac");
    private static final List<String> ALLOWED_PDF_TYPES = List.of(".pdf");

    @Override
    public String storeFile(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new RuntimeException("Fichier vide ou nom de fichier invalide");
        }

        String originalFilename = file.getOriginalFilename();
        long timestamp = System.currentTimeMillis();
        String filename = timestamp + "_" + originalFilename;
        String extension = getFileExtension(originalFilename).toLowerCase();

        if (!isValidFileType(file)) {
            throw new RuntimeException("Type de fichier non autorisé: " + extension);
        }

        try {
            Path targetDir = getTargetDirectory(extension);
            Files.createDirectories(targetDir);
            
            Path targetPath = targetDir.resolve(filename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            return targetPath.toString().replace("\\", "/");
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> storeFiles(List<MultipartFile> files) {
        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                filePaths.add(storeFile(file));
            }
        }
        return filePaths;
    }

    @Override
    public byte[] getFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new RuntimeException("Fichier non trouvé: " + filePath);
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression du fichier: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isValidFileType(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) return false;
        
        String extension = getFileExtension(filename).toLowerCase();
        return ALLOWED_IMAGE_TYPES.contains(extension) ||
               ALLOWED_VIDEO_TYPES.contains(extension) ||
               ALLOWED_AUDIO_TYPES.contains(extension) ||
               ALLOWED_PDF_TYPES.contains(extension);
    }

    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return filename.substring(lastIndexOf);
    }

    private Path getTargetDirectory(String extension) {
        if (ALLOWED_IMAGE_TYPES.contains(extension)) {
            return Paths.get(UPLOAD_DIR, "images");
        } else if (ALLOWED_VIDEO_TYPES.contains(extension)) {
            return Paths.get(UPLOAD_DIR, "videos");
        } else if (ALLOWED_AUDIO_TYPES.contains(extension)) {
            return Paths.get(UPLOAD_DIR, "audios");
        } else if (ALLOWED_PDF_TYPES.contains(extension)) {
            return Paths.get(UPLOAD_DIR, "pdfs");
        } else {
            throw new RuntimeException("Type de fichier non supporté: " + extension);
        }
    }
}