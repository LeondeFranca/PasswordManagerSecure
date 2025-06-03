package passwordmanagersecure.ui;

import passwordmanagersecure.auth.AuthManager;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginUI() {
        setTitle("Login - Gerenciador de Senhas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de campos
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Entrar");
        JButton registerButton = new JButton("Cadastrar");

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        // Ações dos botões
        loginButton.addActionListener(e -> doLogin());

        registerButton.addActionListener(e -> {
            // Abre a tela de cadastro de usuário e mantém esta tela visível
            new UserRegisterUI(this);
            this.setVisible(false);
        });

        setVisible(true);
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha email e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean loggedIn = AuthManager.login(email, password);
            if (!loggedIn) {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro no login: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        // Se chegou aqui, login OK, abrir tela 2FA
        new TwoFAUI(this, email);
        this.dispose();
    }
}
