package com.len.spring.rest.api.spring_rest_api.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.len.spring.rest.api.spring_rest_api.dto.ContactResponse;
import com.len.spring.rest.api.spring_rest_api.dto.CreateContactRequest;
import com.len.spring.rest.api.spring_rest_api.dto.SearchContactRequest;
import com.len.spring.rest.api.spring_rest_api.dto.UpdateContactRequest;
import com.len.spring.rest.api.spring_rest_api.entity.Contact;
import com.len.spring.rest.api.spring_rest_api.entity.User;
import com.len.spring.rest.api.spring_rest_api.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    private ContactResponse toContactResponse(Contact contact) {
        return ContactResponse.builder()
            .id(contact.getId())
            .firstname(contact.getFirstName())
            .lastname(contact.getLastName())
            .email(contact.getEmail())
            .phone(contact.getPhone())
            .build();
    }

    @Transactional
    public ContactResponse create(User user ,CreateContactRequest request) {
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName(request.getFirstname());
        contact.setLastName(request.getLastname());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);

        contactRepository.save(contact);

        return toContactResponse(contact);
    }
    
    @Transactional(readOnly = true)
    public ContactResponse get(User user, String id) {
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        return toContactResponse(contact);
    }

    @Transactional
    public ContactResponse update(User user, UpdateContactRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contact.setFirstName(request.getFirstname());
        contact.setLastName(request.getLastname());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contactRepository.save(contact);

        return toContactResponse(contact);
    }

    public void delete(User user, String contactId) {
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contactRepository.delete(contact);
    }

    // public Page<ContactResponse> search(User user, SearchContactRequest request) {

    // }

}
