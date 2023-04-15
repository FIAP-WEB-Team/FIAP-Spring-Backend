package br.com.fiap.spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import br.com.fiap.spring.model.dto.EmailDTO;
import br.com.fiap.spring.service.SMTPService;


@RestController
@RequestMapping("email")
public class SMTPController {
    private final SMTPService service;

    public SMTPController(SMTPService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmailDTO createTicket(@RequestBody EmailDTO emailDTO) {
        try {
            return service.sendEmail(emailDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
