package com.conexionapirest.controller;

import com.conexionapirest.collectors.EntityAndDTOConvertor;
import com.conexionapirest.model.DTOS.CourseDTO;
import com.conexionapirest.model.entities.Course;
import com.conexionapirest.model.entities.Student;
import com.conexionapirest.model.services.CourseService;
import com.conexionapirest.model.services.InstructorService;
import com.conexionapirest.model.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.conexionapirest.collectors.EntityAndDTOConvertor.courseToDTO;
import static com.conexionapirest.collectors.EntityAndDTOConvertor.dtoToCourse;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses().stream()
                .map(EntityAndDTOConvertor::courseToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if (courseOptional.isPresent()) {
            CourseDTO courseDTO = courseToDTO(courseOptional.get());
            return ResponseEntity.ok(courseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        Course course = dtoToCourse(courseDTO);

        if (course.getInstructor() != null) {
            instructorService.createInstructor(course.getInstructor());
        }

        if (course.getStudents() != null) {
        Set<Student> students = course.getStudents().stream()
                    .map(student -> studentService.getStudentById(student.getId()).orElse(student))
                    .collect(Collectors.toSet());
        course.setStudents(students);
        }


        if (course.getId() == null || !courseService.existCourseById(course.getId())) {
            course = courseService.createCourse(course);
        } else {
            course = courseService.getCourseById(course.getId()).orElse(null);
        }

        assert course != null;
        return ResponseEntity.ok(courseToDTO(course));
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<CourseDTO> addStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Student> studentOptional = studentService.getStudentById(studentId);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOptional.get();
        Student student = studentOptional.get();

        if (course.getStudents().contains(student)) {
            return ResponseEntity.badRequest().build();
        }

        course.getStudents().add(student);
        courseService.createCourse(course);

        return ResponseEntity.ok(courseToDTO(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        Course course= dtoToCourse(courseDTO);
        Course updatedCourse= courseService.updateCourse(id, course);

        return ResponseEntity.ok(courseToDTO(updatedCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (courseService.getCourseById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
