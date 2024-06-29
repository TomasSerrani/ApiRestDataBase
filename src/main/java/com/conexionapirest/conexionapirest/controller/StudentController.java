package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.apirepository.AddressRepository;
import com.conexionapirest.conexionapirest.apirepository.ContactInfoRepository;
import com.conexionapirest.conexionapirest.apirepository.StudentRepository;
import com.conexionapirest.conexionapirest.model.Address;
import com.conexionapirest.conexionapirest.model.ContactInfo;
import com.conexionapirest.conexionapirest.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ContactInfoRepository contactInfoRepository;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Student> getStudent() {
        return studentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // Establish the relationships
        ContactInfo contactInfo = student.getContactInfo();
        Address address = contactInfo.getAddress();

        contactInfo.setStudent(student);
        address.setContactInfo(contactInfo);

        // Save entities in the correct order
        Address savedAddress = addressRepository.save(address);
        contactInfo.setAddress(savedAddress);

        ContactInfo savedContactInfo = contactInfoRepository.save(contactInfo);
        student.setContactInfo(savedContactInfo);

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity.ok(savedStudent);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setName(studentDetails.getName());
            existingStudent.setDateBirth(studentDetails.getDateBirth());

            ContactInfo existingContactInfo = existingStudent.getContactInfo();
            ContactInfo newContactInfo = studentDetails.getContactInfo();

            if (newContactInfo != null) {
                existingContactInfo.setEmail(newContactInfo.getEmail());
                existingContactInfo.setPhone(newContactInfo.getPhone());
                existingContactInfo.setStudent(existingStudent);
            }

            Address existingAddress = existingContactInfo.getAddress();
            Address newAddress = newContactInfo != null ? newContactInfo.getAddress() : null;

            if (newAddress != null) {
                existingAddress.setProvince(newAddress.getProvince());
                existingAddress.setDistrict(newAddress.getDistrict());
                existingAddress.setStreet(newAddress.getStreet());
                existingAddress.setSquare(newAddress.getSquare());
                existingAddress.setHouseNum(newAddress.getHouseNum());
                existingAddress.setZip(newAddress.getZip());
                existingAddress.setContactInfo(existingContactInfo);
            }

            return studentRepository.save(existingStudent);
        } else {
            throw new RuntimeException("Student not found with id " + id);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            studentRepository.delete(optionalStudent.get());
        } else {
            throw new RuntimeException("Student not found with id " + id);
        }
    }
}
