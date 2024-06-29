package com.conexionapirest.conexionapirest.apirepository;

import com.conexionapirest.conexionapirest.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

