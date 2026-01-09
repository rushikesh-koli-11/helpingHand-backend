package com.helpingHands.demo.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    String uploadFile(MultipartFile file) throws IOException;
    String uploadFile(MultipartFile file, String folder) throws IOException;
    void deleteFile(String publicId) throws IOException;
    Map<String, Object> uploadFileWithDetails(MultipartFile file) throws IOException;
}

