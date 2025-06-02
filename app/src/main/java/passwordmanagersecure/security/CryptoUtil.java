package passwordmanagersecure.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Base64;

public class CryptoUtil {
    private static final String AES = "AES";

    // AES Encrypt usando a chave dinâmica segura
    public static String encryptAES(String data) throws Exception {
        String key = KeyManager.getAesKey(); // chave AES já descriptografada
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // AES Decrypt usando a chave dinâmica segura
    public static String decryptAES(String encryptedData) throws Exception {
        String key = KeyManager.getAesKey(); // chave AES já descriptografada
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(original);
    }

    // Hash de senha com bcrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verifica senha com bcrypt
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
