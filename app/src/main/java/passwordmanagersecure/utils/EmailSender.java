package passwordmanagersecure.utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import passwordmanagersecure.security.KeyManager;

public class EmailSender {

    private static String EMAIL;
    private static String PASSWORD;

    static {
        try {
            EMAIL = KeyManager.getEmailUser();
            PASSWORD = KeyManager.getEmailPassword();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao descriptografar as credenciais de email");
        }
    }

    public static void sendEmail(String toEmail, String subject, String messageText) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("✉️ Código de verificação enviado com sucesso para " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
