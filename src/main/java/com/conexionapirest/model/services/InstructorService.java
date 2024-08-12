package com.conexionapirest.model.services;

import com.conexionapirest.apirepository.InstructorRepository;
import com.conexionapirest.model.entities.Course;
import com.conexionapirest.model.entities.Instructor;
import com.conexionapirest.model.services.interfaces.IInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService implements IInstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Boolean existInstructorById(Long id){
        return instructorRepository.existsById(id);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Optional<Instructor> getInstructorById(Long id) {
        return instructorRepository.findById(id);
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor updateInstructor(Long id, Instructor instructorDetails) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (optionalInstructor.isPresent()) {
            Instructor instructor = optionalInstructor.get();
            instructor.setName(instructorDetails.getName());
            instructor.getCourses().clear();
            for (Course course : instructorDetails.getCourses()) {
                course.setInstructor(instructor);
                instructor.addCourse(course);}
            return instructorRepository.save(instructor);
        } else {
            throw new RuntimeException("Instructor not found with id " + id);
        }
    }

    @Override
    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
}
