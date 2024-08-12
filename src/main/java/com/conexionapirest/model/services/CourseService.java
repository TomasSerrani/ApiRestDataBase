package com.conexionapirest.model.services;

import com.conexionapirest.apirepository.CourseRepository;
import com.conexionapirest.model.entities.Course;
import com.conexionapirest.model.services.interfaces.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public  Boolean existCourseById(Long id){
        return courseRepository.existsById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setName(courseDetails.getName());
            course.setInstructor(courseDetails.getInstructor());
            course.setStudents(courseDetails.getStudents());
            return courseRepository.save(course);
        } else {
            throw new RuntimeException("Course not found with id " + id);
        }
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
