package kg.attractor.microgram.service.impl;

import kg.attractor.microgram.dto.ImageDto;
import kg.attractor.microgram.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @SneakyThrows
    @Override
    public String saveUploadFile(MultipartFile file, String subDir){
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get("data/" + subDir);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + fileName);
        if (!Files.exists(filePath)) Files.createFile(filePath);
        log.info("Начало сохранения файла: {} в директорию: {}", fileName, subDir);
        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
            log.info("Файл успешно записан по пути: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Ошибка при записи файла {}: {}", fileName, e.getMessage());
        }

        return fileName;
    }

    @Override
    public ResponseEntity<?> downloadFile(String fileName, String subDir, MediaType mediaType) {
        try {
            byte[] image = Files.readAllBytes(Paths.get("data/" + subDir + "/" + fileName));

            Resource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("File not found");
        }
    }

    @Override
    public ResponseEntity<?> getById(String fileName) {
        return downloadFile(fileName, "images", MediaType.IMAGE_JPEG);
    }

    @Override
    public void create(ImageDto imageDto) {
        log.info("Создание новой записи изображения");
        String fileName = saveUploadFile(imageDto.getFile(), "images");
        log.debug("Сгенерированное имя файла для Image: {}", fileName);
    }

    @Override
    public void deleteFile(String fileName, String subDir) {
        try {
            Path filePath = Paths.get("data/" + subDir + "/" + fileName);

            if (!fileName.equals("default-avatar.png")) {
                Files.deleteIfExists(filePath);
                log.info("Файл успешно удален: {}", filePath.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Не удалось удалить файл {}. Ошибка: {}", fileName, e.getMessage());
        }
    }
}
