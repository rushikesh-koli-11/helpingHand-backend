package com.helpingHands.demo.services.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.helpingHands.demo.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        return uploadFile(file, null);
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        Map<String, Object> params = ObjectUtils.asMap(
            "resource_type", "auto"
        );
        
        if (folder != null && !folder.isEmpty()) {
            params.put("folder", folder);
        }

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        return (String) uploadResult.get("secure_url");
    }

    @Override
    public void deleteFile(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    @Override
    public Map<String, Object> uploadFileWithDetails(MultipartFile file) throws IOException {
        Map<String, Object> params = ObjectUtils.asMap(
            "resource_type", "auto"
        );
        return cloudinary.uploader().upload(file.getBytes(), params);
    }
}

