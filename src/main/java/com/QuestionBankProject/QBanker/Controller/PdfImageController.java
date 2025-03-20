package com.QuestionBankProject.QBanker.Controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/")
public class PdfImageController {

    private final String UPLOAD_DIR = "uploads"; // Ensure this matches where you store images

    public String encodeFileName(String filename){
        return filename.replace(" ","%");
    }


    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException, MalformedURLException {

        //convert file name and add % where space is there
        filename=encodeFileName(filename);

        System.out.println();
        System.out.println(filename);
        System.out.println();


        Path imagePath = Paths.get(UPLOAD_DIR).resolve(filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build(); // Return 404 if image is not found
        }

        // Detect media type
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        MediaType mediaType = switch (extension) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };

        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }
}
