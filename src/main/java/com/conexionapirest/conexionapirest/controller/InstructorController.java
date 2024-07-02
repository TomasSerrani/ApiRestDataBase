package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.DTOS.*;
import com.conexionapirest.conexionapirest.apirepository.CourseRepository;
import com.conexionapirest.conexionapirest.apirepository.InstructorRepository;
import com.conexionapirest.conexionapirest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;
    
    private InstructorDTO convertToDTO(Instructor instructor) {
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setName(instructor.getName());

        Set<CourseDTO> courseDTOSet = instructor.getCourses().stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            return courseDTO;
        }).collect(Collectors.toSet());
        instructorDTO.setCourses(courseDTOSet);

        return instructorDTO;
    }

    private Instructor convertToEntity(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setId(instructorDTO.getId());
        instructor.setName(instructorDTO.getName());

        Set<Course> courseSet = instructorDTO.getCourses().stream().map(courseDTO -> {
            Course course = new Course();
            course.setId(courseDTO.getId());
            course.setName(courseDTO.getName());
            return course;
        }).collect(Collectors.toSet());
        instructor.setCourses(courseSet);

        return instructor;
    }

    @GetMapping
    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@RequestBody InstructorDTO instructorDTO) {

        Instructor instructor = convertToEntity(instructorDTO);

        Set<Course> courses = new HashSet<>();
        if (instructor.getCourses() != null) {
            courses = instructor.getCourses().stream()
                    .map(student -> courseRepository.findById(student.getId()).orElse(student))
                    .collect(Collectors.toSet());
        }
        instructor.setCourses(courses);

        if (instructor.getId() == null || !instructorRepository.existsById(instructor.getId())) {
            instructor = instructorRepository.save(instructor);
        } else {
            instructor = instructorRepository.findById(instructor.getId()).orElse(null);
        }

        return ResponseEntity.ok(convertToDTO(instructor));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructorDetails) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);

        if (optionalInstructor.isPresent()) {
            Instructor existingInstructor = optionalInstructor.get();
            existingInstructor.setName(instructorDetails.getName());

            existingInstructor.getCourses().clear();
            for (Course course : instructorDetails.getCourses()) {
                course.setInstructor(existingInstructor);
                existingInstructor.addCourse(course);
            }

            return ResponseEntity.ok(instructorRepository.save(existingInstructor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);

        if (optionalInstructor.isPresent()) {
            instructorRepository.delete(optionalInstructor.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
