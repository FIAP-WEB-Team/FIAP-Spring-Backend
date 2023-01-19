package br.com.fiap.spring.service.firebase_impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseApp;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;

@Service
public class FirebaseInitialize {
    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount = null;
            for (String path : new String[]{"src/main/resources/config/service_key.json", "service_key.json"})
                if (new File(path).isFile()) {
                    serviceAccount = new FileInputStream(path);
                    break;
                }
            if (serviceAccount == null)
                throw new FileNotFoundException("Could not find the service key file");

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
