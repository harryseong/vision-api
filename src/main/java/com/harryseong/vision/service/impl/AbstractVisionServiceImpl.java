package com.harryseong.vision.service.impl;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.harryseong.vision.pojo.EntityAnnotationDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.harryseong.vision.constants.Constants.*;

@Slf4j
public abstract class AbstractVisionServiceImpl {

    public List<EntityAnnotationDto> mapToEntityAnnotationDtos(List<AnnotateImageResponse> responses) {
        List<EntityAnnotationDto> entityAnnotationDtos = new ArrayList<>();

        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                log.error("Error: {}", res.getError().getMessage());
                continue;
            }

            for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                EntityAnnotationDto entityAnnotationDto = new EntityAnnotationDto();
                annotation.getAllFields().forEach((k, v) -> {
                    switch (k.getName()) {
                        case ENTITY_MID -> entityAnnotationDto.setMid(v.toString());
                        case ENTITY_DESCRIPTION -> entityAnnotationDto.setDescription(v.toString());
                        case ENTITY_SCORE -> entityAnnotationDto.setScore(v.toString());
                        case ENTITY_TOPICALITY -> entityAnnotationDto.setTopicality(v.toString());
                        default -> log.warn("Unrecognized EntityAnnotation key: {}", v.toString());
                    }
                });
                entityAnnotationDtos.add(entityAnnotationDto);
            }
        }

        return entityAnnotationDtos;
    }
}
