package passwordmanagersecure.auth;

import passwordmanagersecure.utils.EmailSender;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class TwoFactorAuthManager {

    private static class VerificationCode {
        String code;
        LocalDateTime expiration;

        VerificationCode(String code, LocalDateTime expiration) {
            this.code = code;
            this.expiration = expiration;
        }
    }

    private static final HashMap<String, VerificationCode> codes = new HashMap<>();
    private static final Random random = new Random();
    private static final int EXPIRATION_MINUTES = 5;

    public static void sendVerificationCode(String userEmail) {
        String code = String.format("%06d", random.nextInt(999999));
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);

        codes.put(userEmail, new VerificationCode(code, expiration));

        String subject = "Seu código de verificação (2FA)";
        String message = "Seu código de verificação é: " + code + "\nEste código expira em 5 minutos.";

        EmailSender.sendEmail(userEmail, subject, message);
    }

    public static boolean verifyCode(String userEmail, String inputCode) {
        VerificationCode vc = codes.get(userEmail);

        if (vc == null) return false;

        if (LocalDateTime.now().isAfter(vc.expiration)) {
            codes.remove(userEmail); 
            System.out.println("⏰ Código expirado.");
            return false;
        }

        return vc.code.equals(inputCode);
    }
}
