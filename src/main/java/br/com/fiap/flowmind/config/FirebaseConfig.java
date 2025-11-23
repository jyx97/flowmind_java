package br.com.fiap.flowmind.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {

            String base64 = System.getenv("FIREBASE_KEY_BASE64");

            if (base64 == null || base64.isEmpty()) {
                throw new RuntimeException("FIREBASE_KEY_BASE64 não encontrada");
            }

            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            ByteArrayInputStream serviceAccountStream = new ByteArrayInputStream(decodedBytes);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .setProjectId("flowmind-df0fc")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firestore conectado via Base64!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
