package br.com.fiap.spring.service.firebase_impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseApp;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FirebaseInitialize {
    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/config/service_key.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://golapi-5c457.web.app/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
