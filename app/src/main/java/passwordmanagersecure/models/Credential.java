package passwordmanagersecure.models;

import passwordmanagersecure.security.CryptoUtil;

public class Credential {
    private String service;        
    private String username;       
    private String encryptedPassword; 

    
    public Credential(String service, String username, String plainPassword) {
        this.service = service;
        this.username = username;
        try {
            this.encryptedPassword = CryptoUtil.encryptAES(plainPassword); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public String getService() {
        return service;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    
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
