package br.com.fiap.spring.service;

import br.com.fiap.spring.model.dto.EmailDTO;

public interface SMTPService {
    public EmailDTO sendEmail(EmailDTO email);
}
