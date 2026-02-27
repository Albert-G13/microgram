package kg.attractor.microgram.service;

import kg.attractor.microgram.dto.ImageDto;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    @SneakyThrows
    String saveUploadFile(MultipartFile file, String subDir);

    ResponseEntity<?> downloadFile(String fileName, String subDir, MediaType mediaType);

    ResponseEntity<?> getById(String fileName);

    void create(ImageDto imageDto);

    void deleteFile(String fileName, String subDir);
}
