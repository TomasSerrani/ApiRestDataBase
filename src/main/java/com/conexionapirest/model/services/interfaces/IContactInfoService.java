package com.conexionapirest.model.services.interfaces;

import com.conexionapirest.model.entities.ContactInfo;

import java.util.List;
import java.util.Optional;

public interface IContactInfoService {
    List<ContactInfo> getAllContactInfo();
    Optional<ContactInfo> getContactInfoById(Long id);
    ContactInfo createContactInfo(ContactInfo contactInfo);
    ContactInfo updateContactInfo(Long id, ContactInfo contactInfoDetails);
    void deleteContactInfo(Long id);
    Boolean existContactInfoById(Long id);
}
