package com.conexionapirest.model.services.interfaces;
import com.conexionapirest.model.entities.Student;
import java.util.List;
import java.util.Optional;

public interface IStudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    Student createStudent(Student student);
    Student updateStudent(Long id, Student studentDetails);
    void deleteStudent(Long id);
    Boolean existStudentById(Long id);
}
