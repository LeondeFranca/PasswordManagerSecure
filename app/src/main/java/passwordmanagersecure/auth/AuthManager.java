package passwordmanagersecure.auth;

import passwordmanagersecure.firebase.FirebaseUtil;
import passwordmanagersecure.security.CryptoUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;

import java.util.concurrent.ExecutionException;

public class AuthManager {

    // Cadastro de usuário
    public static void register(String email, String plainPassword) {
        try {
            String hashedPassword = CryptoUtil.hashPassword(plainPassword);

            // Cria o usuário no Firebase Auth
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(plainPassword);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            String uid = userRecord.getUid();

            // Armazena os dados no Realtime Database
            DatabaseReference ref = FirebaseUtil.getDatabase().getReference("users").child(uid);
            ref.child("email").setValueAsync(email);
            ref.child("passwordHash").setValueAsync(hashedPassword);

            System.out.println("✅ Usuário registrado com sucesso: " + uid);
        } catch (FirebaseAuthException e) {
            System.err.println("❌ Erro ao registrar usuário: " + e.getMessage());
        }
    }

    // Login com senha e 2FA
    public static boolean login(String email, String plainPassword) {
        try {
            String uid = FirebaseUtil.getUidByEmail(email);
            if (uid == null) {
                System.out.println("❌ Usuário não encontrado.");
                return false;
            }

            DatabaseReference userRef = FirebaseUtil.getDatabase().getReference("users").child(uid);
            DataSnapshot snapshot = FirebaseUtil.getSnapshot(userRef);
            String storedHash = snapshot.child("passwordHash").getValue(String.class);

            if (!CryptoUtil.checkPassword(plainPassword, storedHash)) {
                System.out.println("❌ Senha incorreta.");
                return false;
            }

            // Envia o código 2FA
            TwoFactorAuthManager.sendVerificationCode(email);
            System.out.println("📧 Código 2FA enviado para o e-mail.");

            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Erro no login: " + e.getMessage());
            return false;
        }
    }

    // Verificação do código 2FA
    public static boolean verify2FACode(String email, String code) {
        return TwoFactorAuthManager.verifyCode(email, code);
    }
}
