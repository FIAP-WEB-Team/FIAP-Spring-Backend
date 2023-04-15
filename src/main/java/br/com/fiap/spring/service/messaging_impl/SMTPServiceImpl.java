package br.com.fiap.spring.service.messaging_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.fiap.spring.configuration.SMTPConfig;
import br.com.fiap.spring.model.dto.EmailDTO;
import br.com.fiap.spring.service.SMTPService;

@Service
public class SMTPServiceImpl implements SMTPService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SMTPConfig smtpConfig;

    @Override
    public EmailDTO sendEmail(EmailDTO email) {
        System.out.println("Received from MQ: " + email.subject + " - " + email.body);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(smtpConfig.getUsername());
            message.setSubject(email.subject);
            message.setText(email.body);
            message.setTo(email.to);
            emailSender.send(message);

            return email;
        } catch (Exception ex) {
            System.out.println("Error sending email: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

}
