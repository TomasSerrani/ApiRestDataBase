package com.conexionapirest.model.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class InstructorDTO {

    private Long id;
    private String name;
    private Set<CourseDTO> courses;

}
