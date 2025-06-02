package passwordmanagersecure;

import passwordmanagersecure.auth.AuthManager;
import passwordmanagersecure.firebase.FirebaseUtil;
import passwordmanagersecure.models.Credential;
import passwordmanagersecure.utils.PasswordGenerator;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        // Usando try-with-resources para garantir fechamento do scanner
        try (Scanner scanner = new Scanner(System.in)) {

            // Inicializa Firebase (pode lançar exceções)
            try {
                FirebaseUtil.initialize();
            } catch (Exception e) {
                System.err.println("Falha na inicialização do Firebase: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            System.out.println("==== Cadastro de Usuário ====");
            System.out.print("Digite email: ");
            String email = scanner.nextLine();
            System.out.print("Digite senha: ");
            String password = scanner.nextLine();

            try {
                AuthManager.register(email, password);
            } catch (Exception e) {
                System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
                return;
            }

            System.out.println("\n==== Login de Usuário ====");
            System.out.print("Digite email: ");
            String loginEmail = scanner.nextLine();
            System.out.print("Digite senha: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccess;
            try {
                loginSuccess = AuthManager.login(loginEmail, loginPassword);
            } catch (Exception e) {
                System.err.println("Erro durante login: " + e.getMessage());
                return;
            }

            if (!loginSuccess) {
                System.out.println("Falha no login. Encerrando.");
                return;
            }

            System.out.println("Digite o código 2FA recebido no email:");
            String code = scanner.nextLine();

            boolean verified;
            try {
                verified = AuthManager.verify2FACode(loginEmail, code);
            } catch (Exception e) {
                System.err.println("Erro na verificação 2FA: " + e.getMessage());
                return;
            }

            if (!verified) {
                System.out.println("Código 2FA inválido. Encerrando.");
                return;
            }

            System.out.println("Login e 2FA verificados com sucesso!");

            String userId;
            try {
                userId = FirebaseUtil.getUidByEmail(loginEmail);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Erro ao obter UID do usuário: " + e.getMessage());
                return;
            }

            if (userId == null) {
                System.out.println("Erro: usuário não encontrado após login.");
                return;
            }

            // Teste geração de senha forte
            System.out.println("\nGerando uma senha forte de 12 caracteres...");
            String generatedPassword = PasswordGenerator.generateStrongPassword(12);
            System.out.println("Senha gerada: " + generatedPassword);

            // Criar uma nova credencial e salvar
            Credential newCredential = new Credential("ExampleService", "usuario123", generatedPassword);

            try {
                FirebaseUtil.saveCredential(userId, newCredential);
                System.out.println("Credencial salva no Firebase.");
            } catch (Exception e) {
                System.err.println("Erro ao salvar credencial: " + e.getMessage());
                return;
            }

            // Listar credenciais do usuário
            System.out.println("\nBuscando credenciais do usuário:");
            List<Credential> credentials;
            try {
                credentials = FirebaseUtil.getCredentials(userId);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Erro ao buscar credenciais: " + e.getMessage());
                return;
            }

            for (Credential cred : credentials) {
                System.out.println("-----------------------------");
                System.out.println(cred);
            }

            System.out.println("\n==== Fim dos testes ====");

        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
