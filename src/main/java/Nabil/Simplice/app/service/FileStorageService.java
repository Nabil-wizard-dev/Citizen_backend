package Nabil.Simplice.app.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    String storeFile(MultipartFile file);
    List<String> storeFiles(List<MultipartFile> files);
    byte[] getFile(String filePath);
    boolean deleteFile(String filePath);
    boolean isValidFileType(MultipartFile file);
}