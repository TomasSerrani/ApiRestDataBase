package com.conexionapirest.conexionapirest.DTOS;

import com.conexionapirest.conexionapirest.model.Instructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Getter
@Setter
public class CourseDTO {

    private Long id;
    private String name;
    private Instructor instructor;
    private Set<StudentDTO> students = new HashSet<>();

    public CourseDTO(Long id, String name, Instructor instructor) {
        this.id = id;
        this.instructor = instructor;
        this.name = name;
    }
}
