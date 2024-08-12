package com.conexionapirest.model.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private Long id;
    private String province;
    private String district;
    private String street;
    private String square;
    private String houseNum;
    private String zip;

}
