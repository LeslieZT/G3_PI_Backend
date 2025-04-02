package dh.backend.music_store.controller;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dh.backend.music_store.service.impl.CloudinaryService;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Controlador para exponer endpoints de subida de archivos a cloudinary
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    private CloudinaryService cloudinaryService;

    public UploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }


    //enpoint independiente para subir una imagen al cloudinary
    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image")MultipartFile image){
        Map<String, String> response = new HashMap<>();
        if (image.isEmpty()) {
            response.put("error", "No file uploaded");
            return ResponseEntity.ok(response);
        }
        String imageUrl = cloudinaryService.uploadToStorage(image);

        response.put("url", imageUrl);
        return ResponseEntity.ok(response);
    }


}
