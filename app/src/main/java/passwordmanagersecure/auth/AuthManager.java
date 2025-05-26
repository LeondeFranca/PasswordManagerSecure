package passwordmanagersecure.auth;

import java.util.HashMap;
import java.util.Map;

import passwordmanagersecure.security.CryptoUtil;

public class AuthManager {
    private Map<String, String> userDatabase = new HashMap<>();

    // Cadastra um novo usuário
    public boolean register(String email, String plainPassword) {
        if (userDatabase.containsKey(email)) {
            System.out.println("🚫 Usuário já cadastrado.");
            return false;
        }

        String hashedPassword = CryptoUtil.hashPassword(plainPassword);
        userDatabase.put(email, hashedPassword);
        System.out.println("✅ Usuário registrado com sucesso!");
        return true;
    }

    // Autentica o usuário
    public boolean login(String email, String plainPassword) {
        if (!userDatabase.containsKey(email)) {
            System.out.println("❌ Usuário não encontrado.");
            return false;
        }

        String hashedPassword = userDatabase.get(email);
        boolean isCorrect = CryptoUtil.checkPassword(plainPassword, hashedPassword);

        if (isCorrect) {
            System.out.println("✅ Login bem-sucedido!");
        } else {
            System.out.println("🚫 Senha incorreta.");
        }

        return isCorrect;
    }

    
    public void printUsers() {
        System.out.println("\n📄 Usuários cadastrados:");
        userDatabase.forEach((email, hash) -> {
            System.out.println("- " + email + " | " + hash);
        });
    }
}
