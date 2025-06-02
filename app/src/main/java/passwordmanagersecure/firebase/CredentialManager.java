package passwordmanagersecure.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import passwordmanagersecure.models.Credential;

public class CredentialManager {

    private static final DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    // Salvar nova credencial
    public static boolean salvarCredencial(String userEmail, Credential credential) {
        try {
            String safeEmail = userEmail.replace(".", "_");
            db.child("credentials")
              .child(safeEmail)
              .child(credential.getUsername())
              .setValueAsync(credential);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar credencial: " + e.getMessage());
            return false;
        }
    }
}
