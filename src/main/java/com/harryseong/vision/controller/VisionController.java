package com.harryseong.vision.controller;

import com.harryseong.vision.pojo.EntityAnnotationDto;
import com.harryseong.vision.service.VisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vision")
@Slf4j
public class VisionController {

    @Autowired
    private VisionService visionService;

    @GetMapping("health-check")
    public ResponseEntity<?> healthCheck() {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error encountered during health check: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("annotate-image")
    public ResponseEntity<List<EntityAnnotationDto>> annotateImage(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(visionService.getImageAnnotations(file), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error encountered during image annotation: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
