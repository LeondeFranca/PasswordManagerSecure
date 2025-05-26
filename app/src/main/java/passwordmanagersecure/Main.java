package passwordmanagersecure;

import passwordmanagersecure.auth.AuthManager;

public class Main {
    public static void main(String[] args) {
        AuthManager auth = new AuthManager();

        // 👇 Teste: Cadastrar usuário
        String email = "leon.test@example.com";
        String senha = "SenhaForte123";

        auth.cadastrarUsuario(email, senha);

        // 👇 Teste: Buscar usuário
        auth.buscarUsuarioPorEmail(email);
    }
}
