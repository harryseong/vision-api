package com.harryseong.vision.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EntityAnnotationDto {

    private String mid;
    private String description;
    private String score;
    private String topicality;
}
