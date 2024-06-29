package com.conexionapirest.conexionapirest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "courses_generator")
    @SequenceGenerator(name = "courses_generator",allocationSize = 1)
    private long id;

    @Column(name = "course",nullable = false)
    private String name;

    public Course(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
