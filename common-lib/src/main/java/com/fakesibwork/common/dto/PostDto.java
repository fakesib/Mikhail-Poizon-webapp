package com.fakesibwork.common.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String description;
    private String image_path;
    private String author;
    private LocalDate date;


}
