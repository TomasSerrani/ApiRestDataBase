package com.conexionapirest.collectors;

import com.conexionapirest.model.DTOS.*;
import com.conexionapirest.model.entities.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityAndDTOConvertor {

    public static AddressDTO addressToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setProvince(address.getProvince());
        addressDTO.setDistrict(address.getDistrict());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setSquare(address.getSquare());
        addressDTO.setHouseNum(address.getHouseNum());
        addressDTO.setZip(address.getZip());

        return addressDTO;
    }
    public static Address dtoToAddress(AddressDTO addressDTO) {

        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setProvince(addressDTO.getProvince());
        address.setDistrict(addressDTO.getDistrict());
        address.setStreet(addressDTO.getStreet());
        address.setSquare(addressDTO.getSquare());
        address.setHouseNum(addressDTO.getHouseNum());
        address.setZip(addressDTO.getZip());

        return address;
    }

    public static ContactInfoDTO contactInfoToDTO(ContactInfo contactInfo) {
        ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
        contactInfoDTO.setId(contactInfo.getId());
        contactInfoDTO.setEmail(contactInfo.getEmail());
        contactInfoDTO.setPhone(contactInfo.getPhone());
        return contactInfoDTO;
    }

    public static ContactInfo dtoToContactInfo(ContactInfoDTO contactInfoDTO) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(contactInfoDTO.getId());
        contactInfo.setEmail(contactInfoDTO.getEmail());
        contactInfo.setPhone(contactInfoDTO.getPhone());
        return contactInfo;
    }

    public static StudentDTO studentToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setDateBirth(student.getDateBirth());

        if (student.getContactInfo() != null) {
            ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
            contactInfoDTO.setId(student.getContactInfo().getId());
            contactInfoDTO.setEmail(student.getContactInfo().getEmail());
            contactInfoDTO.setPhone(student.getContactInfo().getPhone());

            if (student.getContactInfo().getAddress() != null) {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setId(student.getContactInfo().getAddress().getId());
                addressDTO.setProvince(student.getContactInfo().getAddress().getProvince());
                addressDTO.setDistrict(student.getContactInfo().getAddress().getDistrict());
                addressDTO.setStreet(student.getContactInfo().getAddress().getStreet());
                addressDTO.setSquare(student.getContactInfo().getAddress().getSquare());
                addressDTO.setHouseNum(student.getContactInfo().getAddress().getHouseNum());
                addressDTO.setZip(student.getContactInfo().getAddress().getZip());

                contactInfoDTO.setAddress(addressDTO);
            }

            studentDTO.setContactInfo(contactInfoDTO);
        }

        Set<CourseDTO> courseDTOSet = student.getCourses().stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            return courseDTO;
        }).collect(Collectors.toSet());
        studentDTO.setCourses(courseDTOSet);

        return studentDTO;
    }

    public static Student dtoToStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setDateBirth(studentDTO.getDateBirth());

        ContactInfoDTO contactInfoDTO = studentDTO.getContactInfo();
        if (contactInfoDTO != null) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setId(contactInfoDTO.getId());
            contactInfo.setEmail(contactInfoDTO.getEmail());
            contactInfo.setPhone(contactInfoDTO.getPhone());

            AddressDTO addressDTO = contactInfoDTO.getAddress();
            if (addressDTO != null) {
                Address address = new Address();
                address.setId(addressDTO.getId());
                address.setProvince(addressDTO.getProvince());
                address.setDistrict(addressDTO.getDistrict());
                address.setStreet(addressDTO.getStreet());
                address.setSquare(addressDTO.getSquare());
                address.setHouseNum(addressDTO.getHouseNum());
                address.setZip(addressDTO.getZip());

                contactInfo.setAddress(address);
            }

            student.setContactInfo(contactInfo);
        }

        Set<Course> courseSet = studentDTO.getCourses().stream().map(courseDTO -> {
            Course course = new Course();
            course.setId(courseDTO.getId());
            course.setName(courseDTO.getName());
            return course;
        }).collect(Collectors.toSet());
        student.setCourses(courseSet);

        return student;
    }

    public static InstructorDTO instructorToDTO(Instructor instructor) {
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setName(instructor.getName());

        Set<CourseDTO> courseDTOSet = instructor.getCourses().stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            return courseDTO;
        }).collect(Collectors.toSet());
        instructorDTO.setCourses(courseDTOSet);

        return instructorDTO;
    }

    public static Instructor dtoToInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setId(instructorDTO.getId());
        instructor.setName(instructorDTO.getName());

        Set<Course> courseSet = instructorDTO.getCourses().stream().map(courseDTO -> {
            Course course = new Course();
            course.setId(courseDTO.getId());
            course.setName(courseDTO.getName());
            return course;
        }).collect(Collectors.toSet());
        instructor.setCourses(courseSet);

        return instructor;
    }

    public static CourseDTO courseToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());

        if (course.getInstructor() != null) {
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setId(course.getInstructor().getId());
            instructorDTO.setName(course.getInstructor().getName());

            courseDTO.setInstructor(instructorDTO);
        }

        Set<StudentDTO> studentDTOSet = course.getStudents().stream().map(student -> {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(student.getId());
            studentDTO.setName(student.getName());
            return studentDTO;
        }).collect(Collectors.toSet());
        courseDTO.setStudents(studentDTOSet);

        return courseDTO;
    }

    public static Course dtoToCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());

        InstructorDTO instructorDTO = courseDTO.getInstructor();
        if (instructorDTO != null) {
            Instructor instructor = new Instructor();
            instructor.setId(instructorDTO.getId());
            instructor.setName(instructorDTO.getName());
            course.setInstructor(instructor);
        }

        Set<Student> studentSet = new HashSet<>();
        if (courseDTO.getStudents() != null) {
            studentSet = courseDTO.getStudents().stream().map(studentDTO -> {
                Student student = new Student();
                student.setId(studentDTO.getId());
                student.setName(studentDTO.getName());
                return student;
            }).collect(Collectors.toSet());
        }
        course.setStudents(studentSet);

        return course;
    }
}
