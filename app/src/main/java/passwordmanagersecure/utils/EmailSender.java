package passwordmanagersecure.utils;

import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSender {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String EMAIL = dotenv.get("EMAIL_SENDER");
    private static final String PASSWORD = dotenv.get("EMAIL_PASSWORD");

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
