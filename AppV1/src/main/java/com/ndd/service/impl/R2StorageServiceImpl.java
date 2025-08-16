package com.ndd.service.impl;

import com.ndd.service.R2StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class R2StorageServiceImpl implements R2StorageService {

    private final S3Client s3Client;

    @Value("${cloudflare.bucket_name}")
    private String bucketName;

    @Value("${cloudflare.public_endpoint_url}")
    private String publicEndpoint;

    public R2StorageServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String original = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new IllegalArgumentException("Filename is missing"))
                    .toLowerCase()
                    .replaceAll("\\s+", "-"); // thay khoảng trắng bằng dấu '-'
            String contentType = Optional.ofNullable(file.getContentType())
                    .orElseThrow(() -> new IllegalArgumentException("Content-Type is unknown"));

            // Nếu key rỗng, tự sinh key
            String ext = getFileExtension(original);
            String folder = decideFolder(ext);
            String key = String.format("%s/%s-%s", folder, UUID.randomUUID(), original);

            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(req, RequestBody.fromBytes(file.getBytes()));

            return getFileUrl(key);

        } catch (IOException e) {
            throw new RuntimeException("File upload to Cloudflare R2 failed", e);
        }
    }

    @Override
    public void deleteByUrl(String url) {
        if (url == null || url.isBlank()) {
            return;
        }
        String key = extractKeyFromUrl(url);
        s3Client.deleteObject(b -> b.bucket(bucketName).key(key));
    }

    private String extractKeyFromUrl(String url) {
        String ep = publicEndpoint.endsWith("/") ? publicEndpoint.substring(0, publicEndpoint.length() - 1) : publicEndpoint;

        String keyPart = url.startsWith(ep) ? url.substring(ep.length()) : url;
        if (keyPart.startsWith("/")) {
            keyPart = keyPart.substring(1);
        }

        return java.net.URLDecoder.decode(keyPart, java.nio.charset.StandardCharsets.UTF_8);
    }

    public String getFileUrl(String key) {
        return publicEndpoint + "/" + key;
    }

    private String getFileExtension(String filename) {
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length() - 1) {
            throw new IllegalArgumentException("Invalid file extension in filename: " + filename);
        }
        return filename.substring(idx + 1);
    }

    private String decideFolder(String ext) {
        ext = ext.toLowerCase();
        switch (ext) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                return "images";
            case "mp3":
            case "wav":
            case "ogg":
                return "audio";
            case "mp4":
            case "mov":
                return "videos";
            case "pdf":
            case "doc":
            case "docx":
            case "txt":
                return "documents";
            default:
                throw new IllegalArgumentException("Unsupported file type: " + ext);
        }
    }
}
