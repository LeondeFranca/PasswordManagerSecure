package passwordmanagersecure;

import passwordmanagersecure.auth.AuthManager;
import passwordmanagersecure.firebase.FirebaseUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializa o Firebase
        FirebaseUtil.initialize();

        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Bem-vindo ao AuthManager ====");
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        System.out.println("Escolha uma opção:");
        System.out.println("1 - Registrar");
        System.out.println("2 - Login");
        System.out.print("Opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                AuthManager.register(email, senha);
                break;
            case "2":
                if (AuthManager.login(email, senha)) {
                    System.out.print("Digite o código 2FA enviado por e-mail: ");
                    String code = scanner.nextLine();

                    if (AuthManager.verify2FACode(email, code)) {
                        System.out.println("✅ Login com 2FA realizado com sucesso!");
                    } else {
                        System.out.println("❌ Código 2FA inválido.");
                    }
                } else {
                    System.out.println("❌ Falha no login.");
                }
                break;
            default:
                System.out.println("❌ Opção inválida.");
                break;
        }

        scanner.close();
    }
}
