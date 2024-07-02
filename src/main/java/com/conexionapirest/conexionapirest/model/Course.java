package com.conexionapirest.conexionapirest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "courses_generator")
    @SequenceGenerator(name = "courses_generator",allocationSize = 1)
    private Long id;

    @Column(name = "course",nullable = false)
    private String name;

    public Course(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Instructor instructor;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
