package br.com.fiap.spring.messaging;

import br.com.fiap.spring.configuration.SMTPConfig;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

@Component
public class RabbitMQReceiver implements MessageListener {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SMTPConfig smtpConfig;

    private void sendEmail(String subject, String body, String to) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(smtpConfig.getUsername());
        message.setSubject(subject);
        message.setText(body);
        message.setTo(to);

        emailSender.send(message);
    }

    @Override
    public void onMessage(Message message) {
        String messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("Received from MQ: " + messageContent);

        try {
            var object = (LinkedHashMap<String, String>) new JSONParser(messageContent).parse();

            String subject = (String) object.get("subject");
            String body = (String) object.get("body");
            String to = (String) object.get("to");

            sendEmail(subject, body, to);
        } catch (Exception ex) {
            System.out.println("Error sending email: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
