package com.conexionapirest.model.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoDTO {

    private Long id;
    private String email;
    private String phone;
    private AddressDTO address;
}