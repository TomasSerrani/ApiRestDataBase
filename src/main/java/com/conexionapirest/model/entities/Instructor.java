package com.conexionapirest.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "instructor_generator")
    @SequenceGenerator(name = "instructor_generator",allocationSize = 1)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
    @OneToMany(mappedBy = "instructor",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Course> courses= new HashSet<>();

    public Instructor(String name) {
        this.name = name;
    }

    public void addCourse (Course course) {
        courses.add(course);
        course.setInstructor(this);
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
