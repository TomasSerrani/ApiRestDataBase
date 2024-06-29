package com.conexionapirest.conexionapirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contact_info")
public class ContactInfo {
    @Id
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    @OneToOne(mappedBy = "contactInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    public ContactInfo(String email, String phone, Address address) {
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", student=" + student +
                ", address=" + address +
                '}';
    }
}
