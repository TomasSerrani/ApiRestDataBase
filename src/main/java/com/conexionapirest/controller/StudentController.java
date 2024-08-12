package com.conexionapirest.controller;

import com.conexionapirest.collectors.EntityAndDTOConvertor;
import com.conexionapirest.model.DTOS.StudentDTO;
import com.conexionapirest.apirepository.CourseRepository;
import com.conexionapirest.model.entities.Address;
import com.conexionapirest.model.entities.Course;
import com.conexionapirest.model.entities.Student;
import com.conexionapirest.model.services.AddressService;
import com.conexionapirest.model.services.ContactInfoService;
import com.conexionapirest.model.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.conexionapirest.collectors.EntityAndDTOConvertor.dtoToStudent;
import static com.conexionapirest.collectors.EntityAndDTOConvertor.studentToDTO;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents().stream()
                .map(EntityAndDTOConvertor::studentToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Optional<Student> studentOptional = studentService.getStudentById(id);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        StudentDTO studentDTO = studentToDTO(studentOptional.get());
        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = dtoToStudent(studentDTO);

        if (student.getContactInfo() != null) {
            Address address = student.getContactInfo().getAddress();
            if (address != null) {
                addressService.createAddress(address);
            }
            contactInfoService.createContactInfo(student.getContactInfo());
        }

        Set<Course> courses = student.getCourses().stream()
                .map(course -> courseRepository.findById(course.getId()).orElse(course))
                .collect(Collectors.toSet());
        student.setCourses(courses);

        if (student.getId() == null || !studentService.existStudentById(student.getId())) {
            student = studentService.createStudent(student);
        } else {
            student = studentService.getStudentById(student.getId()).orElse(null);
        }

        return ResponseEntity.ok(studentToDTO(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        Student student = dtoToStudent(studentDTO);
        Student updatedStudent= studentService.updateStudent(id,student);

        return ResponseEntity.ok(studentToDTO(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        if (!studentService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        contactInfoService.deleteContactInfo(id);
       addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}