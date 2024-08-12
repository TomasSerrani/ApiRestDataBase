package com.conexionapirest.model.services;

import com.conexionapirest.apirepository.ContactInfoRepository;
import com.conexionapirest.model.entities.ContactInfo;
import com.conexionapirest.model.services.interfaces.IContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactInfoService implements IContactInfoService {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Override
    public Boolean existContactInfoById(Long id){
        return contactInfoRepository.existsById(id);
    }

    @Override
    public List<ContactInfo> getAllContactInfo() {
        return contactInfoRepository.findAll();
    }

    @Override
    public Optional<ContactInfo> getContactInfoById(Long id) {
        return contactInfoRepository.findById(id);
    }

    @Override
    public ContactInfo createContactInfo(ContactInfo contactInfo) {
        return contactInfoRepository.save(contactInfo);
    }

    @Override
    public ContactInfo updateContactInfo(Long id, ContactInfo contactInfoDetails) {
        Optional<ContactInfo> optionalContactInfo = contactInfoRepository.findById(id);
        if (optionalContactInfo.isPresent()) {
            ContactInfo contactInfo = optionalContactInfo.get();
            contactInfo.setPhone(contactInfoDetails.getPhone());
            contactInfo.setEmail(contactInfoDetails.getEmail());
            contactInfo.setAddress(contactInfoDetails.getAddress());
            return contactInfoRepository.save(contactInfo);
        } else {
            throw new RuntimeException("ContactInfo not found with id " + id);
        }
    }

    @Override
    public void deleteContactInfo(Long id) {
        contactInfoRepository.deleteById(id);
    }
}
