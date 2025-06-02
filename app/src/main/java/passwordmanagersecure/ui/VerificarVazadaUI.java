package passwordmanagersecure.ui;

import passwordmanagersecure.security.PasswordLeakChecker;

import javax.swing.*;
import java.awt.*;

public class VerificarVazadaUI extends JFrame {

    public VerificarVazadaUI(JFrame parent) {
        setTitle("Verificar Senha Vazada");
        setSize(400, 180);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Digite a senha para verificar:");
        JPasswordField senhaField = new JPasswordField();

        JButton verificarBtn = new JButton("Verificar");
        JButton voltarBtn = new JButton("Voltar");

        verificarBtn.addActionListener(e -> {
            String senha = new String(senhaField.getPassword());
            if (senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite uma senha.");
                return;
            }

            boolean vazada = PasswordLeakChecker.isPasswordPwned(senha);
            if (vazada) {
                JOptionPane.showMessageDialog(this, "⚠️ Senha foi vazada! Use outra.");
            } else {
                JOptionPane.showMessageDialog(this, "✅ Senha não encontrada em vazamentos.");
            }
        });

        voltarBtn.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        setLayout(new GridLayout(3, 1, 10, 10));
        add(label);
        add(senhaField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(verificarBtn);
        buttonPanel.add(voltarBtn);
        add(buttonPanel);

        setVisible(true);
    }
}
