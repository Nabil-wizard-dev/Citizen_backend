package Nabil.Simplice.app.controller;

import Nabil.Simplice.app.service.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/download/**")
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) {
        try {
            String filePath = request.getRequestURI().substring("/api/files/download/".length());
            
            byte[] fileContent = fileStorageService.getFile(filePath);
            
            String contentType = getContentType(filePath);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + getFileName(filePath) + "\"")
                    .body(fileContent);
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/view/**")
    public ResponseEntity<byte[]> viewFile(HttpServletRequest request) {
        try {
            String filePath = request.getRequestURI().substring("/api/files/view/".length());
            
            byte[] fileContent = fileStorageService.getFile(filePath);
            
            String contentType = getContentType(filePath);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(fileContent);
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> deleteFile(HttpServletRequest request) {
        try {
            String filePath = request.getRequestURI().substring("/api/files/".length());
            
            boolean deleted = fileStorageService.deleteFile(filePath);
            
            if (deleted) {
                return ResponseEntity.ok("Fichier supprimé avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fichier non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression du fichier");
        }
    }

    private String getContentType(String filePath) {
        try {
            Path path = Paths.get(filePath);
            String contentType = Files.probeContentType(path);
            return contentType != null ? contentType : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

    private String getFileName(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }
}