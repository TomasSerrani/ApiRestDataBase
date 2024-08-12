package com.conexionapirest.controller;

import com.conexionapirest.collectors.EntityAndDTOConvertor;
import com.conexionapirest.model.DTOS.ContactInfoDTO;
import com.conexionapirest.model.entities.ContactInfo;
import com.conexionapirest.model.services.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.conexionapirest.collectors.EntityAndDTOConvertor.contactInfoToDTO;
import static com.conexionapirest.collectors.EntityAndDTOConvertor.dtoToContactInfo;

@RestController
@RequestMapping("/contactInfo")
public class ContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @GetMapping
    public List<ContactInfoDTO> getAllContactInfo() {
        return contactInfoService.getAllContactInfo().stream()
                .map(EntityAndDTOConvertor::contactInfoToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactInfoDTO> getContactInfoById(@PathVariable Long id) {
        Optional<ContactInfo> contactInfoOptional = contactInfoService.getContactInfoById(id);
        if (!contactInfoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ContactInfoDTO contactInfoDTO = contactInfoToDTO(contactInfoOptional.get());
        return ResponseEntity.ok(contactInfoDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContactInfoDTO> updateContactInfo(@PathVariable Long id, @RequestBody ContactInfoDTO contactInfoDTO) {
        ContactInfo contactInfo = dtoToContactInfo(contactInfoDTO);
        ContactInfo updatedContactInfo=contactInfoService.updateContactInfo(id,contactInfo);
        return ResponseEntity.ok(contactInfoToDTO(updatedContactInfo));
    }
}
