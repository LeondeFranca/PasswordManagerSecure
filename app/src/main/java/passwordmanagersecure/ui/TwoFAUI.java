package passwordmanagersecure.ui;

import passwordmanagersecure.auth.AuthManager;
import passwordmanagersecure.firebase.FirebaseUtil;
import passwordmanagersecure.utils.Session;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class TwoFAUI extends JFrame {

    private JTextField codeField;
    private String email;
    private JFrame parent;

    public TwoFAUI(JFrame parent, String email) {
        this.parent = parent;
        this.email = email;

        setTitle("Autenticação 2FA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Digite o código 2FA recebido no email:"));
        codeField = new JTextField();
        panel.add(codeField);

        JButton verifyButton = new JButton("Verificar");
        add(panel, BorderLayout.CENTER);
        add(verifyButton, BorderLayout.SOUTH);

        verifyButton.addActionListener(e -> verifyCode());

        setVisible(true);
    }

    private void verifyCode() {
        String code = codeField.getText().trim();

        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o código 2FA.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean verified = AuthManager.verify2FACode(email, code);
            if (!verified) {
                JOptionPane.showMessageDialog(this, "Código 2FA inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na verificação 2FA: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        // Obtém userId e salva na Session
        String userId;
        try {
            userId = FirebaseUtil.getUidByEmail(email);
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (userId == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session.setCurrentUserId(userId);

        JOptionPane.showMessageDialog(this, "Login e 2FA verificados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Fecha a tela de login anterior para não deixar janelas abertas
        if (parent != null) {
            parent.dispose();
        }

        // Abre a tela principal
        new MainUI();

        // Fecha esta janela de 2FA
        this.dispose();
    }
}
