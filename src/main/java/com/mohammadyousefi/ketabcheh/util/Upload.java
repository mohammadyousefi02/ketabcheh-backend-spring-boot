package com.mohammadyousefi.ketabcheh.util;

import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.ExpectationFailed;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Upload {
    private static final String UPLOAD_DIR = "src/main/resources/static";

    private Upload() {
    }

    private static Path getUploadPath(String directory) {
        return Paths.get(UPLOAD_DIR + "/" + directory).toAbsolutePath().normalize();
    }

    public static String uploadHandler(MultipartFile file, String directory) {
        try {
            Path uploadPath = getUploadPath(directory);
            Files.createDirectories(uploadPath);
            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);
            return fileName;
        } catch (Exception e) {
            throw new ExpectationFailed(ErrorMessages.expectationFailed());
        }
    }

    public static Resource downloadHandler(String fileName, String directory) {
        Path uploadPath = getUploadPath(directory);
        Path filePath = uploadPath.resolve(fileName);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) return resource;
            else throw new NotFoundException(ErrorMessages.notFound("file", "name"));
        } catch (Exception e) {
            throw new ExpectationFailed(ErrorMessages.expectationFailed());
        }

    }
}
