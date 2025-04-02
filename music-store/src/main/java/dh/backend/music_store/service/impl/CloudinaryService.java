package dh.backend.music_store.service.impl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dh.backend.music_store.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;


    //metodo que sube la imagen y devuelve la url absoluta.
    public String uploadToStorage(MultipartFile image){
        if (image.isEmpty() || image == null){
            throw new BadRequestException("Debe subir un archivo.");
        }
        String fileType = image.getContentType();
        if(fileType == null || !fileType.startsWith("image/")){
            throw new BadRequestException("Solo se permiten archivos de tipo imagen.");
        }

        try{
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        }catch (IOException e){
            throw new RuntimeException("Error al procesar la imagen.");
        }
    }

    //https://res.cloudinary.com/dwzugg5gm/image/upload/v1742010100/y7uldppl6v2dm9h9d2vl.png
}