package com.len.spring.rest.api.spring_rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.len.spring.rest.api.spring_rest_api.dto.ContactResponse;
import com.len.spring.rest.api.spring_rest_api.dto.CreateContactRequest;
import com.len.spring.rest.api.spring_rest_api.dto.WebResponse;
import com.len.spring.rest.api.spring_rest_api.entity.User;
import com.len.spring.rest.api.spring_rest_api.service.ContactService;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(
        path = "/api/contacts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user, @RequestBody CreateContactRequest request) {
        ContactResponse contactResponse = contactService.create(user, request);
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

}