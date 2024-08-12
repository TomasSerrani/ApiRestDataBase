package com.conexionapirest.model.services.interfaces;

import com.conexionapirest.model.entities.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Long id);
    Course createCourse(Course course);
    Course updateCourse(Long id, Course courseDetails);
    void deleteCourse(Long id);
    Boolean existCourseById(Long id);
}
