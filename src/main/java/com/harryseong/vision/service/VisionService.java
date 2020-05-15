package com.harryseong.vision.service;

import com.harryseong.vision.pojo.EntityAnnotationDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VisionService {

    List<EntityAnnotationDto> getImageAnnotations(MultipartFile file) throws IOException;
}
