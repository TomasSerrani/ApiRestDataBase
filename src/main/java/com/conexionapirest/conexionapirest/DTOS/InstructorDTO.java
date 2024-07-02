package com.conexionapirest.conexionapirest.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class InstructorDTO {

    private Long id;
    private String name;
    private Set<CourseDTO> courses;

}
