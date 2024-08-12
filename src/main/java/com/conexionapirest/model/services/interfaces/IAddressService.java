package com.conexionapirest.model.services.interfaces;

import com.conexionapirest.model.entities.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressService {
    List<Address> getAllAddresses();
    Optional<Address> getAddressById(Long id);
    Address createAddress(Address address);
    Address updateAddress(Long id, Address addressDetails);
    void deleteAddress(Long id);
}
