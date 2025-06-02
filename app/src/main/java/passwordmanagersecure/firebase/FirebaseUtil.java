package passwordmanagersecure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.io.InputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import passwordmanagersecure.security.KeyManager; // importar o KeyManager

public class FirebaseUtil {
    private static boolean initialized = false;
    private static FirebaseDatabase realtimeDatabase;

    // URL do Firebase criptografada — troque esse valor pelo seu da saída do MainKeyEncryptor
    private static final String ENCRYPTED_FIREBASE_URL = "DQ1fOXYny2v+RWvJc0SCUjIupkCF67w8qUUoOjU6lzT0NhEZHZXC1LhG2aNqCydA4YLtUQTftxvyzXj+bEHWJg==";

    public static void initialize() {
        if (initialized) return;

        try {
            InputStream serviceAccount = FirebaseUtil.class.getClassLoader()
                .getResourceAsStream("serviceAccountKey.json");

            if (serviceAccount == null) {
                System.err.println("❌ serviceAccountKey.json não encontrado!");
                return;
            }

            String decryptedUrl;
            try {
                decryptedUrl = KeyManager.decrypt(ENCRYPTED_FIREBASE_URL);
            } catch (Exception e) {
                System.err.println("Erro ao descriptografar URL do Firebase: " + e.getMessage());
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(decryptedUrl)  // usa o URL descriptografado
                .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);
            realtimeDatabase = FirebaseDatabase.getInstance(app);

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

    public static FirebaseDatabase getDatabase() {
        if (!initialized) {
            throw new IllegalStateException("Firebase não inicializado! Chame FirebaseUtil.initialize() antes.");
        }
        return realtimeDatabase;
    }

    public static String getUidByEmail(String email) throws InterruptedException, ExecutionException {
        DatabaseReference usersRef = getDatabase().getReference("users");

        CompletableFuture<String> future = new CompletableFuture<>();

        Query query = usersRef.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        future.complete(child.getKey());
                        return;
                    }
                }
                future.complete(null);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future.get();
    }

    public static DataSnapshot getSnapshot(DatabaseReference ref) throws InterruptedException, ExecutionException {
        CompletableFuture<DataSnapshot> future = new CompletableFuture<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                future.complete(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future.get();
    }
}
