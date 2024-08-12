package com.conexionapirest.model.services;

import com.conexionapirest.apirepository.StudentRepository;
import com.conexionapirest.model.entities.Student;
import com.conexionapirest.model.services.interfaces.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public  Boolean  existStudentById(Long id){
        return studentRepository.existsById(id);
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(studentDetails.getName());
            student.setContactInfo(studentDetails.getContactInfo());
            student.setCourses(studentDetails.getCourses());
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with id " + id);
        }
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
