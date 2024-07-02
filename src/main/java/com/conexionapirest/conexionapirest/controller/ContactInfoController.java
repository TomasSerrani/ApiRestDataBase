package com.conexionapirest.conexionapirest.controller;

import com.conexionapirest.conexionapirest.DTOS.ContactInfoDTO;
import com.conexionapirest.conexionapirest.apirepository.ContactInfoRepository;
import com.conexionapirest.conexionapirest.model.ContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contactInfo")
public class ContactInfoController {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    private ContactInfoDTO convertToDTO(ContactInfo contactInfo) {
        ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
        contactInfoDTO.setId(contactInfo.getId());
        contactInfoDTO.setEmail(contactInfo.getEmail());
        contactInfoDTO.setPhone(contactInfo.getPhone());
        return contactInfoDTO;
    }

    private ContactInfo convertToEntity(ContactInfoDTO contactInfoDTO) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(contactInfoDTO.getId());
        contactInfo.setEmail(contactInfoDTO.getEmail());
        contactInfo.setPhone(contactInfoDTO.getPhone());
        return contactInfo;
    }

    @GetMapping
    public List<ContactInfoDTO> getAllContactInfo() {
        return contactInfoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactInfoDTO> getContactInfoById(@PathVariable Long id) {
        Optional<ContactInfo> contactInfoOptional = contactInfoRepository.findById(id);
        if (!contactInfoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ContactInfoDTO contactInfoDTO = convertToDTO(contactInfoOptional.get());
        return ResponseEntity.ok(contactInfoDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContactInfoDTO> updateContactInfo(@PathVariable Long id, @RequestBody ContactInfoDTO contactInfoDTO) {
        Optional<ContactInfo> contactInfoOptional = contactInfoRepository.findById(id);
        if (!contactInfoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ContactInfo contactInfo = convertToEntity(contactInfoDTO);
        contactInfo.setId(id);

        ContactInfo updatedContactInfo = contactInfoRepository.save(contactInfo);
        return ResponseEntity.ok(convertToDTO(updatedContactInfo));
    }
}
