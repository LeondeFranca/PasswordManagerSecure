package passwordmanagersecure.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class KeyManager {
    private static final String AES = "AES";

    // Esta senha mestra pode ser obfuscada (ex: codificada em Base64) mas aqui vamos deixar simples
    private static final String MASTER_PASSWORD = "MinhaSenhaForte123!"; // Troque por uma sua

    // Derivação da chave AES (16 bytes fixos da senha mestre)
    private static SecretKeySpec getKey() {
        byte[] keyBytes = MASTER_PASSWORD.substring(0, 16).getBytes(); // Simples, só para uso acadêmico
        return new SecretKeySpec(keyBytes, AES);
    }

    public static String decrypt(String encryptedBase64) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] decoded = Base64.getDecoder().decode(encryptedBase64);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    // Só se quiser criptografar dados e gerar os Base64 uma vez
    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String getAesKey() throws Exception {
    // Substitua pelo valor que você gerou no MainKeyEncryptor
    String encryptedAesKey = "IA8Ixp6IFYby+c+Z8Nj9Wx00R6T3vklRTJKuZfoLV5A=";
    return decrypt(encryptedAesKey);
}
}


