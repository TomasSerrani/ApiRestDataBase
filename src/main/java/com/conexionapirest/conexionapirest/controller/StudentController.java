package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.DTOS.AddressDTO;
import com.conexionapirest.conexionapirest.DTOS.ContactInfoDTO;
import com.conexionapirest.conexionapirest.DTOS.CourseDTO;
import com.conexionapirest.conexionapirest.DTOS.StudentDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setDateBirth(student.getDateBirth());

        if (student.getContactInfo() != null) {
            ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
            contactInfoDTO.setId(student.getContactInfo().getId());
            contactInfoDTO.setEmail(student.getContactInfo().getEmail());
            contactInfoDTO.setPhone(student.getContactInfo().getPhone());

            if (student.getContactInfo().getAddress() != null) {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setId(student.getContactInfo().getAddress().getId());
                addressDTO.setProvince(student.getContactInfo().getAddress().getProvince());
                addressDTO.setDistrict(student.getContactInfo().getAddress().getDistrict());
                addressDTO.setStreet(student.getContactInfo().getAddress().getStreet());
                addressDTO.setSquare(student.getContactInfo().getAddress().getSquare());
                addressDTO.setHouseNum(student.getContactInfo().getAddress().getHouseNum());
                addressDTO.setZip(student.getContactInfo().getAddress().getZip());

                contactInfoDTO.setAddress(addressDTO);
            }

            studentDTO.setContactInfo(contactInfoDTO);
        }

        Set<CourseDTO> courseDTOSet = student.getCourses().stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            return courseDTO;
        }).collect(Collectors.toSet());
        studentDTO.setCourses(courseDTOSet);

        return studentDTO;
    }

    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setDateBirth(studentDTO.getDateBirth());

        ContactInfoDTO contactInfoDTO = studentDTO.getContactInfo();
        if (contactInfoDTO != null) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setId(contactInfoDTO.getId());
            contactInfo.setEmail(contactInfoDTO.getEmail());
            contactInfo.setPhone(contactInfoDTO.getPhone());

            AddressDTO addressDTO = contactInfoDTO.getAddress();
            if (addressDTO != null) {
                Address address = new Address();
                address.setId(addressDTO.getId());
                address.setProvince(addressDTO.getProvince());
                address.setDistrict(addressDTO.getDistrict());
                address.setStreet(addressDTO.getStreet());
                address.setSquare(addressDTO.getSquare());
                address.setHouseNum(addressDTO.getHouseNum());
                address.setZip(addressDTO.getZip());

                contactInfo.setAddress(address);
            }

            student.setContactInfo(contactInfo);
        }

        Set<Course> courseSet = studentDTO.getCourses().stream().map(courseDTO -> {
            Course course = new Course();
            course.setId(courseDTO.getId());
            course.setName(courseDTO.getName());
            return course;
        }).collect(Collectors.toSet());
        student.setCourses(courseSet);

        return student;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        StudentDTO studentDTO = convertToDTO(studentOptional.get());
        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);

        if (student.getContactInfo() != null) {
            Address address = student.getContactInfo().getAddress();
            if (address != null) {
                addressRepository.save(address);
            }
            contactInfoRepository.save(student.getContactInfo());
        }

        Set<Course> courses = student.getCourses().stream()
                .map(course -> courseRepository.findById(course.getId()).orElse(course))
                .collect(Collectors.toSet());
        student.setCourses(courses);

        if (student.getId() == null || !studentRepository.existsById(student.getId())) {
            student = studentRepository.save(student);
        } else {
            student = studentRepository.findById(student.getId()).orElse(null);
        }

        return ResponseEntity.ok(convertToDTO(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Student student = convertToEntity(studentDTO);
        student.setId(id); // Asegurarse de que el ID sea el correcto

        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(convertToDTO(updatedStudent));
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
