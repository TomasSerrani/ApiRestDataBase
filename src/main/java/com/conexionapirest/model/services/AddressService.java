package com.conexionapirest.model.services;

import com.conexionapirest.apirepository.AddressRepository;
import com.conexionapirest.model.entities.Address;
import com.conexionapirest.model.services.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, Address addressDetails) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setProvince(addressDetails.getProvince());
            address.setDistrict(addressDetails.getDistrict());
            address.setStreet(addressDetails.getStreet());
            address.setSquare(addressDetails.getSquare());
            address.setHouseNum(addressDetails.getHouseNum());
            address.setZip(addressDetails.getZip());
            return addressRepository.save(address);
        } else {
            throw new RuntimeException("Address not found with id " + id);
        }
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
