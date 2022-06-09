package com.tv.tvapi.helper;

import com.tv.tvapi.exception.FileUploadException;
import com.tv.tvapi.response.BaseResponse;
import com.tv.tvapi.response.FileUploadResponse;
import com.tv.tvapi.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("FileUploadHelper")
@RequiredArgsConstructor
public class FileUploadHelper {

    private final FileStorageService fileStorageService;

    public ResponseEntity<?> uploadFile(MultipartFile file) {
        try {
            FileUploadResponse fileUploadResponse = fileStorageService.saveImage(file);
            return BaseResponse.success(fileUploadResponse, "Upload file success!");
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest().body(BaseResponse.badRequest(e.getMessage()));
        }
    }
}
