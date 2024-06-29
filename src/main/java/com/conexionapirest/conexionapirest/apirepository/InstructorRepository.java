package com.conexionapirest.conexionapirest.apirepository;

import com.conexionapirest.conexionapirest.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}

