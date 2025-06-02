package passwordmanagersecure.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class PasswordLeakChecker {

    public static boolean isPasswordPwned(String password) {
        try {
            // SHA-1 hash
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = sha1.digest(password.getBytes("UTF-8"));
            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02X", b));
            }

            String fullHash = hash.toString();
            String prefix = fullHash.substring(0, 5);
            String suffix = fullHash.substring(5);

            // Faz a requisição à API
            URL url = new URL("https://api.pwnedpasswords.com/range/" + prefix);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith(suffix)) {
                    in.close();
                    return true; // Encontrou a senha na lista de vazadas
                }
            }
            in.close();
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao verificar senha na API: " + e.getMessage());
            return false;
        }
    }
}
