package passwordmanagersecure.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuthException;
import passwordmanagersecure.firebase.FirebaseUtil;

public class AuthManager {

    public AuthManager() {
        FirebaseUtil.initialize(); // Garante que o Firebase esteja conectado
    }

    public void cadastrarUsuario(String email, String senha) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
            .setEmail(email)
            .setPassword(senha);

        try {
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("✅ Usuário cadastrado com sucesso: " + userRecord.getUid());
        } catch (FirebaseAuthException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public void buscarUsuarioPorEmail(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            System.out.println("👤 Usuário encontrado: " + userRecord.getEmail());
        } catch (FirebaseAuthException e) {
            System.err.println("❌ Usuário não encontrado: " + e.getMessage());
        }
    }
}
