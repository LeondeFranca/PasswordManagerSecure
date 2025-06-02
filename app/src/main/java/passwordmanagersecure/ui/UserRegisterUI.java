package passwordmanagersecure.ui;

import passwordmanagersecure.auth.AuthManager;

import javax.swing.*;
import java.awt.*;

public class UserRegisterUI extends JFrame {

    public UserRegisterUI(JFrame parent) {
        setTitle("Cadastro de Usuário");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField();

        JButton cadastrarButton = new JButton("Cadastrar");
        JButton cancelarButton = new JButton("Cancelar");

        cadastrarButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                AuthManager.register(email, password);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                dispose();
                parent.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        setLayout(new GridLayout(4, 2, 10, 10));

        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(cadastrarButton);
        add(cancelarButton);

        setVisible(true);
    }
}
