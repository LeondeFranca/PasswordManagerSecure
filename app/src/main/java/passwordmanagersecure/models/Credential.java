package passwordmanagersecure.models;

import passwordmanagersecure.security.CryptoUtil;

public class Credential {
    private String service;
    private String username;
    private String encryptedPassword;

    // Construtor padrão necessário para Firebase deserialização
    public Credential() {}

    public Credential(String service, String username, String plainPassword) {
        this.service = service;
        this.username = username;
        try {
            this.encryptedPassword = CryptoUtil.encryptAES(plainPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getService() {
        return service;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    // Setters (necessários para Firebase)
    public void setService(String service) {
        this.service = service;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    // Método para retornar senha já descriptografada (não usado pelo Firebase)
    public String getDecryptedPassword() {
        try {
            return CryptoUtil.decryptAES(encryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPassword(String newPlainPassword) {
        try {
            this.encryptedPassword = CryptoUtil.encryptAES(newPlainPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Service: " + service + "\nUsername: " + username + "\nPassword: " + getDecryptedPassword();
    }
}
