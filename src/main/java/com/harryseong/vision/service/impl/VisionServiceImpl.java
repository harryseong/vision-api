package com.harryseong.vision.service.impl;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.harryseong.vision.pojo.EntityAnnotationDto;
import com.harryseong.vision.service.VisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VisionServiceImpl extends AbstractVisionServiceImpl implements VisionService {

    public List<EntityAnnotationDto> getImageAnnotations(MultipartFile file) throws IOException {

        // Instantiates a client
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // Reads the image file into memory
            byte[] data = file.getBytes();
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            return this.mapToEntityAnnotationDtos(responses);
        }
    }
}
