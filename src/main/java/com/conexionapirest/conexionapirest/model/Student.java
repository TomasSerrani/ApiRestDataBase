package com.conexionapirest.conexionapirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="dateBirth", nullable = false)
    private LocalDate dateBirth;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "student")
    @JsonIgnore
    private ContactInfo contactInfo;


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

