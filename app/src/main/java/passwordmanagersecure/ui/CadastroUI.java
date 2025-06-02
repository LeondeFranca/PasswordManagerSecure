package passwordmanagersecure.ui;

import passwordmanagersecure.firebase.FirebaseUtil;
import passwordmanagersecure.models.Credential;
import passwordmanagersecure.utils.Session;


import javax.swing.*;
import java.awt.*;

public class CadastroUI extends JFrame {

    public CadastroUI(JFrame parent) {
        setTitle("Cadastrar Nova Senha");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel serviceLabel = new JLabel("Serviço:");
        JTextField serviceField = new JTextField();

        JLabel usernameLabel = new JLabel("Usuário:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField();

        JButton salvarButton = new JButton("Salvar");
        JButton voltarButton = new JButton("Voltar");

        salvarButton.addActionListener(e -> {
            String service = serviceField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!service.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                Credential credential = new Credential(service, username, password);
                FirebaseUtil.saveCredential(Session.getCurrentUserId(), credential);
                JOptionPane.showMessageDialog(this, "Senha cadastrada com sucesso!");
                dispose();
                parent.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        });

        voltarButton.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        add(serviceLabel); add(serviceField);
        add(usernameLabel); add(usernameField);
        add(passwordLabel); add(passwordField);
        add(salvarButton); add(voltarButton);

        setVisible(true);
    }
}
