package br.com.fiap.spring.configuration;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.Properties;

@Configuration
public class SMTPConfig {

    private String username;
    private String password;

    @PostConstruct
    private void loadEmailInfo() {
        try {
            FileInputStream emailSecret = null;
            for (String path : new String[] { "src/main/resources/config/gmail_secret.json", "gmail_secret.json" })
                if (new File(path).isFile()) {
                    emailSecret = new FileInputStream(path);
                    break;
                }
            var object = (LinkedHashMap<String, String>) new JSONParser(emailSecret).parse();
            username = (String) object.get("username");
            password = (String) object.get("password");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
