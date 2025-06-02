package passwordmanagersecure.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import passwordmanagersecure.models.Credential;
import passwordmanagersecure.security.KeyManager;

public class FirebaseUtil {

    private static boolean initialized = false;
    private static FirebaseDatabase realtimeDatabase;

    public static void initialize() {
        if (initialized) return;

        try {
            // Obtém o JSON descriptografado diretamente do KeyManager
            String serviceAccountJson = KeyManager.getServiceAccountJson();

            // Cria InputStream a partir do JSON em memória
            InputStream serviceAccountStream = new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8));

            // Descriptografa a URL do Firebase
            String decryptedUrl = KeyManager.getFirebaseUrl();

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .setDatabaseUrl(decryptedUrl)
                .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);
            realtimeDatabase = FirebaseDatabase.getInstance(app);

            initialized = true;
            System.out.println("✅ Firebase conectado com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao conectar com o Firebase: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao descriptografar dados do Firebase: " + e.getMessage());
        }
    }

    public static FirebaseDatabase getDatabase() {
        if (!initialized) {
            throw new IllegalStateException("Firebase não inicializado! Chame FirebaseUtil.initialize() antes.");
        }
        return realtimeDatabase;
    }

    public static void saveCredential(String userId, Credential credential) {
        if (!initialized) {
            throw new IllegalStateException("Firebase não inicializado! Chame FirebaseUtil.initialize() antes.");
        }

        DatabaseReference credentialsRef = getDatabase()
            .getReference("users")
            .child(userId)
            .child("credentials")
            .push();

        credentialsRef.setValueAsync(credential);
    }

    public static List<Credential> getCredentials(String userId) throws InterruptedException, ExecutionException {
        if (!initialized) {
            throw new IllegalStateException("Firebase não inicializado! Chame FirebaseUtil.initialize() antes.");
        }

        DatabaseReference credentialsRef = getDatabase()
            .getReference("users")
            .child(userId)
            .child("credentials");

        CompletableFuture<DataSnapshot> future = new CompletableFuture<>();

        credentialsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                future.complete(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        DataSnapshot snapshot = future.get();

        Map<String, Credential> credentialsMap = new HashMap<>();

        for (DataSnapshot child : snapshot.getChildren()) {
            Credential cred = child.getValue(Credential.class);
            credentialsMap.put(child.getKey(), cred);
        }

        return new ArrayList<>(credentialsMap.values());
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
