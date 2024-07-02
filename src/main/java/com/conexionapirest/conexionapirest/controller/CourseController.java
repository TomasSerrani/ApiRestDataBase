package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.DTOS.CourseDTO;
import com.conexionapirest.conexionapirest.DTOS.StudentDTO;
import com.conexionapirest.conexionapirest.apirepository.CourseRepository;
import com.conexionapirest.conexionapirest.apirepository.InstructorRepository;
import com.conexionapirest.conexionapirest.apirepository.StudentRepository;
import com.conexionapirest.conexionapirest.model.Course;
import com.conexionapirest.conexionapirest.model.Instructor;
import com.conexionapirest.conexionapirest.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());

        if (course.getInstructor() != null) {
            Instructor instructorDTO = new Instructor();
            instructorDTO.setId(course.getInstructor().getId());
            instructorDTO.setName(course.getInstructor().getName());

            courseDTO.setInstructor(instructorDTO);
        }

        Set<StudentDTO> studentDTOSet = course.getStudents().stream().map(student -> {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(student.getId());
            studentDTO.setName(student.getName());
            return studentDTO;
        }).collect(Collectors.toSet());
        courseDTO.setStudents(studentDTOSet);

        return courseDTO;
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());

        Instructor instructorDTO = courseDTO.getInstructor();
        if (instructorDTO != null) {
            Instructor instructor = new Instructor();
            instructor.setId(instructorDTO.getId());
            instructor.setName(instructorDTO.getName());
            course.setInstructor(instructor);
        }

        Set<Student> studentSet = new HashSet<>();
        if (courseDTO.getStudents() != null) {
            studentSet = courseDTO.getStudents().stream().map(studentDTO -> {
                Student student = new Student();
                student.setId(studentDTO.getId());
                student.setName(studentDTO.getName());
                return student;
            }).collect(Collectors.toSet());
        }
        course.setStudents(studentSet);

        return course;
    }


    @GetMapping
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            CourseDTO courseDTO = convertToDTO(courseOptional.get());
            return ResponseEntity.ok(courseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);

        if (course.getInstructor() != null) {
            instructorRepository.save(course.getInstructor());
        }

        Set<Student> students = new HashSet<>();
        if (course.getStudents() != null) {
            students = course.getStudents().stream()
                    .map(student -> studentRepository.findById(student.getId()).orElse(student))
                    .collect(Collectors.toSet());
        }
        course.setStudents(students);

        if (course.getId() == null || !courseRepository.existsById(course.getId())) {
            course = courseRepository.save(course);
        } else {
            course = courseRepository.findById(course.getId()).orElse(null);
        }

        return ResponseEntity.ok(convertToDTO(course));
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<CourseDTO> addStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOptional.get();
        Student student = studentOptional.get();

        if (course.getStudents().contains(student)) {
            return ResponseEntity.badRequest().build();
        }

        course.getStudents().add(student);
        courseRepository.save(course);

        return ResponseEntity.ok(convertToDTO(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (!courseOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOptional.get();
        course.setName(courseDTO.getName());
        course.setInstructor(courseDTO.getInstructor());

        Course updatedCourse = courseRepository.save(course);
        return ResponseEntity.ok(convertToDTO(updatedCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (!courseOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        courseRepository.delete(courseOptional.get());
        return ResponseEntity.noContent().build();
    }
}
