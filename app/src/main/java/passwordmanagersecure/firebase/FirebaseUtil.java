package passwordmanagersecure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.cloud.firestore.Firestore;

import java.io.InputStream;
import java.io.IOException;

public class FirebaseUtil {
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;

        try {
            InputStream serviceAccount = FirebaseUtil.class.getClassLoader()
                .getResourceAsStream("serviceAccountKey.json");

            if (serviceAccount == null) {
                System.err.println("❌ serviceAccountKey.json não encontrado!");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);
            initialized = true;

            System.out.println("✅ Firebase conectado com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao conectar com o Firebase: " + e.getMessage());
        }
    }

    public static Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }
}
