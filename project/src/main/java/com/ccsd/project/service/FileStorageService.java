/*
    Filename: FileStorageService.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
 */
package com.ccsd.project.service;

import com.ccsd.project.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final AppConfig appConfig;

    @Autowired
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir, AppConfig appConfig) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.appConfig = appConfig;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileName = getUniqueFileName(originalFileName);
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            String fileUrl = appConfig.getUploadUrl() + "/uploads/" + fileName;
            return fileUrl;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private String getUniqueFileName(String originalFileName) {
        String fileName = originalFileName;
        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileName = originalFileName.substring(0, dotIndex);
            fileExtension = originalFileName.substring(dotIndex);
        }

        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return fileName + "_" + currentDateTime + fileExtension;
    }
}
