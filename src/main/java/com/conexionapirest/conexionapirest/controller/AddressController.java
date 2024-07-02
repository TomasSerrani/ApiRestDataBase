package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.DTOS.AddressDTO;
import com.conexionapirest.conexionapirest.apirepository.AddressRepository;
import com.conexionapirest.conexionapirest.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    private AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setProvince(address.getProvince());
        addressDTO.setDistrict(address.getDistrict());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setSquare(address.getSquare());
        addressDTO.setHouseNum(address.getHouseNum());
        addressDTO.setZip(address.getZip());

        return addressDTO;
    }
    private Address convertToEntity(AddressDTO addressDTO) {

            Address address = new Address();
            address.setId(addressDTO.getId());
            address.setProvince(addressDTO.getProvince());
            address.setDistrict(addressDTO.getDistrict());
            address.setStreet(addressDTO.getStreet());
            address.setSquare(addressDTO.getSquare());
            address.setHouseNum(addressDTO.getHouseNum());
            address.setZip(addressDTO.getZip());

        return address;
    }

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (!addressOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        AddressDTO addressDTO = convertToDTO(addressOptional.get());
        return ResponseEntity.ok(addressDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (!addressOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Address address = convertToEntity(addressDTO);
        address.setId(id);

        Address updatedAddress = addressRepository.save(address);
        return ResponseEntity.ok(convertToDTO(updatedAddress));
    }

}
