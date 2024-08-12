package com.conexionapirest.model.services.interfaces;

import com.conexionapirest.model.entities.Instructor;

import java.util.List;
import java.util.Optional;

public interface IInstructorService {
    List<Instructor> getAllInstructors();
    Optional<Instructor> getInstructorById(Long id);
    Instructor createInstructor(Instructor instructor);
    Instructor updateInstructor(Long id, Instructor instructorDetails);
    void deleteInstructor(Long id);
    Boolean existInstructorById(Long id);
}