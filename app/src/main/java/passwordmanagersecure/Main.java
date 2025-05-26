package passwordmanagersecure;

import passwordmanagersecure.auth.AuthManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("🔐 Testando AuthManager:");

        AuthManager auth = new AuthManager();

        
        System.out.println("\nRegistrando usuário:");
        boolean reg1 = auth.register("leon.trindade@gmail.com", "senhaSegura123");
        System.out.println("Registro 1 (deve ser true): " + reg1);

        boolean reg2 = auth.register("leon.trindade@gmail.com", "tentativaRepetida");
        System.out.println("Registro 2 (deve ser false - usuário duplicado): " + reg2);

        // Teste login
        System.out.println("\nTestando login:");
        boolean login1 = auth.login("leon.trindade@gmail.com", "senhaErrada");
        System.out.println("Login com senha errada (deve ser false): " + login1);

        boolean login2 = auth.login("leon.trindade@gmail.com", "senhaSegura123");
        System.out.println("Login com senha correta (deve ser true): " + login2);

        
        auth.printUsers();
    }
}
