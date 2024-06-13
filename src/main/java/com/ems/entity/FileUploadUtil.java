package com.ems.entity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    public static void saveFile(String uploadDir, String fileName, MultipartFile multiPartFile) throws IOException {
        if (multiPartFile == null || fileName == null || uploadDir == null) {
            throw new IllegalArgumentException("Null parameters provided.");
        }

        logger.info("Saving file: {} to directory: {}", fileName, uploadDir);

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            logger.info("Creating directory: {}", uploadDir);
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multiPartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("File saved successfully: {}", filePath);
        } catch (IOException e) {
            logger.error("Error occurred while saving file: {}", e.getMessage());
            throw new IOException("Could not save file: " + fileName, e);
        }
    }
}
