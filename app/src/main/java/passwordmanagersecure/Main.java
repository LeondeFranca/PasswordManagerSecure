package passwordmanagersecure;

import passwordmanagersecure.utils.EmailSender;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando teste do EmailSender...");

        // Troque esse email para um que você queira enviar a mensagem (pode ser o seu)
        String emailDestino = "leon.sport55@gmail.com";
        String assunto = "Teste de envio de email";
        String mensagem = "Esse é um email de teste enviado pelo EmailSender.";

        EmailSender.sendEmail(emailDestino, assunto, mensagem);

        System.out.println("Teste do EmailSender finalizado.");
    }
}
