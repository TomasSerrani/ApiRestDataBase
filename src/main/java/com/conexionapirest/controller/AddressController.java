package com.conexionapirest.controller;

import com.conexionapirest.collectors.EntityAndDTOConvertor;
import com.conexionapirest.model.DTOS.AddressDTO;
import com.conexionapirest.model.entities.Address;
import com.conexionapirest.model.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.conexionapirest.collectors.EntityAndDTOConvertor.addressToDTO;
import static com.conexionapirest.collectors.EntityAndDTOConvertor.dtoToAddress;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses().stream()
                .map(EntityAndDTOConvertor::addressToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        Optional<Address> addressOptional = addressService.getAddressById(id);
        if (!addressOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        AddressDTO addressDTO = addressToDTO(addressOptional.get());
        return ResponseEntity.ok(addressDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        Address address = dtoToAddress(addressDTO);
        Address updatedAddress= addressService.updateAddress(id, address);

        return ResponseEntity.ok(addressToDTO(updatedAddress));
    }

}
