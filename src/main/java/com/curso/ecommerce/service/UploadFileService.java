package com.curso.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {
    private String folder= "images//";

    public String saveImage(MultipartFile file){
        if (!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(folder + file.getOriginalFilename());
                Files.write(path, bytes);
                return path.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "default.jpg";
    }
    public void deleteImage(String nombre){
        String ruta = "images//";
        File file = new File(ruta + nombre);
        file.delete();
    }
}
