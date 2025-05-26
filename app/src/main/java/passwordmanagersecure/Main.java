package passwordmanagersecure;

import passwordmanagersecure.auth.TwoFactorAuthManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Simula um e-mail
        String userEmail = "leon.sport55@gmail.com";

        System.out.println("🔐 Enviando código 2FA para " + userEmail);
        TwoFactorAuthManager.sendVerificationCode(userEmail);

        System.out.print("📥 Digite o código recebido no e-mail: ");
        String inputCode = scanner.nextLine();

        boolean isValid = TwoFactorAuthManager.verifyCode(userEmail, inputCode);

        if (isValid) {
            System.out.println("✅ Autenticado com sucesso!");
        } else {
            System.out.println("❌ Código inválido ou expirado.");
        }

        scanner.close();
    }
}
