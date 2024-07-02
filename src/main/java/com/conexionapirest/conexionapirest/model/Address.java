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
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "square")
    private String square;

    @Column(name = "houseNum", nullable = false)
    private String houseNum;

    @Column(name = "zip", nullable = false)
    private String zip;

    @OneToOne
    @JoinColumn(name = "contact_info_id")
    @JsonIgnore
    private ContactInfo contactInfo;

    public Address(String province, String district, String street, String square, String zip, String houseNum) {
        this.province = province;
        this.district = district;
        this.street = street;
        this.square = square;
        this.zip = zip;
        this.houseNum = houseNum;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", square='" + square + '\'' +
                ", houseNum=" + houseNum +
                ", zip='" + zip + '\'' +
                ", contactInfo=" + contactInfo +
                '}';
    }
}
