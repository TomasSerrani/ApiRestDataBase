package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.apirepository.CourseRepository;
import com.conexionapirest.conexionapirest.apirepository.InstructorRepository;
import com.conexionapirest.conexionapirest.model.Course;
import com.conexionapirest.conexionapirest.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Instructor> getStudent() {
        return instructorRepository.findAll();
    }

    @PostMapping
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        for (Course course : instructor.getCourses()) {
            course.setInstructor(instructor);
        }
        return instructorRepository.save(instructor);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Instructor updateInstructor(@PathVariable("id") Long id, @RequestBody Instructor instructorDetails) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);

        if (optionalInstructor.isPresent()) {
            Instructor existingInstructor = optionalInstructor.get();
            existingInstructor.setName(instructorDetails.getName());

            existingInstructor.getCourses().clear();
            for (Course course : instructorDetails.getCourses()) {
                course.setInstructor(existingInstructor);
                existingInstructor.addCourse(course);
            }

            return instructorRepository.save(existingInstructor);
        } else {
            throw new RuntimeException("Instructor not found with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable Long id) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);

        if (optionalInstructor.isPresent()) {
            instructorRepository.delete(optionalInstructor.get());
        } else {
            throw new RuntimeException("Instructor not found with id " + id);
        }
    }
}
