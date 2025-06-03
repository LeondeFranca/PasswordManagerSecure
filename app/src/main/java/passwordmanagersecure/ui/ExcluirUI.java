package passwordmanagersecure.ui;

import passwordmanagersecure.firebase.FirebaseUtil;
import passwordmanagersecure.utils.UserSession;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class ExcluirUI extends JFrame {

    public ExcluirUI(JFrame parent) {
        setTitle("Excluir Senha");
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Usuário:");
        JTextField usernameField = new JTextField();

        JButton excluirBtn = new JButton("Excluir");
        JButton voltarBtn = new JButton("Voltar");

        excluirBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome de usuário.");
                return;
            }

            try {
                boolean excluido = FirebaseUtil.deleteCredentialByUsername(UserSession.getCurrentUserId(), username);
                if (excluido) {
                    JOptionPane.showMessageDialog(this, "Credencial excluída com sucesso!");
                    dispose();
                    parent.setVisible(true);  // volta para MainUI após exclusão
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
                }
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir credencial.");
            }
        });

        voltarBtn.addActionListener(e -> {
            dispose();
            parent.setVisible(true);  // volta para MainUI ao clicar em voltar
        });

        setLayout(new GridLayout(3, 1, 10, 10));
        add(label);
        add(usernameField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(excluirBtn);
        buttonPanel.add(voltarBtn);
        add(buttonPanel);

        setVisible(true);
    }
}
