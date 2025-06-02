package passwordmanagersecure.utils;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public static String generateStrongPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }

        List<Character> chars = new ArrayList<>();

        // Garante pelo menos 1 de cada tipo
        chars.add(getRandomChar(UPPERCASE));
        chars.add(getRandomChar(LOWERCASE));
        chars.add(getRandomChar(DIGITS));
        chars.add(getRandomChar(SYMBOLS));

        // Preenche o restante aleatoriamente
        String allChars = UPPERCASE + LOWERCASE + DIGITS + SYMBOLS;
        for (int i = chars.size(); i < length; i++) {
            chars.add(getRandomChar(allChars));
        }

        // Embaralha
        Collections.shuffle(chars, random);

        // Converte para String
        StringBuilder password = new StringBuilder();
        for (char c : chars) {
            password.append(c);
        }

        return password.toString();
    }

    private static char getRandomChar(String source) {
        return source.charAt(random.nextInt(source.length()));
    }
}
