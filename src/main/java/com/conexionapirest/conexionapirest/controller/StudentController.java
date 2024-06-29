package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.apirepository.AddressRepository;
import com.conexionapirest.conexionapirest.apirepository.ContactInfoRepository;
import com.conexionapirest.conexionapirest.apirepository.CourseRepository;
import com.conexionapirest.conexionapirest.apirepository.StudentRepository;
import com.conexionapirest.conexionapirest.model.Address;
import com.conexionapirest.conexionapirest.model.ContactInfo;
import com.conexionapirest.conexionapirest.model.Course;
import com.conexionapirest.conexionapirest.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Student> getStudent() {
        return studentRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        ContactInfo contactInfo = student.getContactInfo();
        Address address= contactInfo.getAddress();

        contactInfo.setStudent(student);
        address.setContactInfo(contactInfo);

        Set<Course> courses = new HashSet<>();
        for (Course course : student.getCourses()) {
            courses.add(courseRepository.findById(course.getId()).orElse(course));
        }
        student.setCourses(courses);

        return studentRepository.save(student);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();
        student.setName(studentDetails.getName());
        student.setDateBirth(studentDetails.getDateBirth());

        ContactInfo contactInfo = studentDetails.getContactInfo();
        if (contactInfo != null) {
            contactInfo.setStudent(student);
            Address address = contactInfo.getAddress();
            if (address != null) {
                address.setContactInfo(contactInfo);
                addressRepository.save(address);
            }
            contactInfoRepository.save(contactInfo);
        }

        // Update the courses if they exist
        if (studentDetails.getCourses() != null) {
            Set<Course> courses = new HashSet<>();
            for (Course course : studentDetails.getCourses()) {
                courses.add(courseRepository.findById(course.getId()).orElse(course));
            }
            student.setCourses(courses);
        }

        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.delete(studentOptional.get());
        return ResponseEntity.noContent().build();
    }
}

