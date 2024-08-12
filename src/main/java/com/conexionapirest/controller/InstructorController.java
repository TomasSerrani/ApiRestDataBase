package com.conexionapirest.controller;

import com.conexionapirest.collectors.EntityAndDTOConvertor;
import com.conexionapirest.model.DTOS.InstructorDTO;
import com.conexionapirest.model.entities.Course;
import com.conexionapirest.model.entities.Instructor;
import com.conexionapirest.model.services.CourseService;
import com.conexionapirest.model.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.conexionapirest.collectors.EntityAndDTOConvertor.dtoToInstructor;
import static com.conexionapirest.collectors.EntityAndDTOConvertor.instructorToDTO;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<InstructorDTO> getAllInstructors() {
        return instructorService.getAllInstructors().stream()
                .map(EntityAndDTOConvertor::instructorToDTO)
                .collect(Collectors.toList());
    }
    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@RequestBody InstructorDTO instructorDTO) {

        Instructor instructor = dtoToInstructor(instructorDTO);

        if (instructor.getCourses() != null) {
        Set<Course> courses = instructor.getCourses().stream()
                    .map(course -> courseService.getCourseById(course.getId()).orElse(course))
                    .collect(Collectors.toSet());
            instructor.setCourses(courses);
        }


        if (instructor.getId() == null || !instructorService.existInstructorById(instructor.getId())) {
            instructor = instructorService.createInstructor(instructor);
        } else {
            instructor = instructorService.getInstructorById(instructor.getId()).orElse(null);
        }

        assert instructor != null;
        return ResponseEntity.ok(instructorToDTO(instructor));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody InstructorDTO instructorDTO) {
        if (instructorService.getInstructorById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Instructor instructor=dtoToInstructor(instructorDTO);
        Instructor updatedInstructor= instructorService.updateInstructor(id, instructor);

        return ResponseEntity.ok(instructorService.createInstructor(updatedInstructor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        if (instructorService.getInstructorById(id).isPresent()) {
            instructorService.deleteInstructor(id);
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.notFound().build();
    }
}
