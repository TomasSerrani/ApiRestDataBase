package com.conexionapirest.model.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class StudentDTO {

    private Long id;
    private String name;
    private LocalDate dateBirth;
    private ContactInfoDTO contactInfo;
    private Set<CourseDTO> courses;
}
