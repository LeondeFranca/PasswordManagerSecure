package passwordmanagersecure.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class KeyManager {

    private static final String AES = "AES";
    private static final String MASTER_PASSWORD = "1234567890abcdef1234567890abcdef"; 

    // 🔐 Dados criptografados (Base64)
    private static final String ENCRYPTED_JSON = "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";
    private static final String ENCRYPTED_FIREBASE_URL = "psgoFSrE5YL5Hw6NMkBxIWa1IoxtEpf4GjPjcMLX0qiSsOtOmjjUZbXXuEqYdlwvbp+fF9C7f3M//86UxXrdcg==";
    private static final String ENCRYPTED_AES_KEY = "+57x7dfNpKfqFQmMFN59bQLexWUsAhX5OvafAJ8PbVg=";
    private static final String ENCRYPTED_EMAIL_USER = "1fYVusi7Ci80vmhlKsRfidiEjUphNXGfRJ5VhHk9WR0=";
    private static final String ENCRYPTED_EMAIL_PASS = "9I8sjxlSAMLBy1VeeSXMsE0HncTUn9Uhd/u73RJnZ6g=";

    private static String decrypt(String encryptedBase64, String password) throws Exception {
        byte[] keyBytes = password.substring(0, 16).getBytes(); // AES-128
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedBase64);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    public static String getDecryptedJson() throws Exception {
        return decrypt(ENCRYPTED_JSON, MASTER_PASSWORD);
    }

    // ✅ Alias para compatibilidade com FirebaseUtil
    public static String getServiceAccountJson() throws Exception {
        return getDecryptedJson();
    }

    public static String getFirebaseUrl() throws Exception {
        return decrypt(ENCRYPTED_FIREBASE_URL, MASTER_PASSWORD);
    }

    public static String getAesKey() throws Exception {
        return decrypt(ENCRYPTED_AES_KEY, MASTER_PASSWORD);
    }

    public static String getEmailUser() throws Exception {
        return decrypt(ENCRYPTED_EMAIL_USER, MASTER_PASSWORD);
    }

    public static String getEmailPassword() throws Exception {
        return decrypt(ENCRYPTED_EMAIL_PASS, MASTER_PASSWORD);
    }
}
