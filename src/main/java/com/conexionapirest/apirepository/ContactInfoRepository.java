package com.conexionapirest.apirepository;

import com.conexionapirest.model.entities.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
}
