package com.conexionapirest.conexionapirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="dateBirth", nullable = false)
    private LocalDate dateBirth;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContactInfo contactInfo;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns= @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    public Student(String name, LocalDate dateBirth, ContactInfo contactInfo) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateBirth=" + dateBirth +
                ", contactInfo=" + contactInfo +
                '}';
    }
}

