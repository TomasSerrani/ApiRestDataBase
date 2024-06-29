package com.conexionapirest.conexionapirest.apirepository;

import com.conexionapirest.conexionapirest.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}

