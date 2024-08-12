package com.conexionapirest.model.DTOS;

import com.conexionapirest.model.entities.Instructor;
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
    private InstructorDTO instructor;
    private Set<StudentDTO> students = new HashSet<>();

    public CourseDTO(Long id, String name, InstructorDTO instructor) {
        this.id = id;
        this.instructor = instructor;
        this.name = name;
    }
}
