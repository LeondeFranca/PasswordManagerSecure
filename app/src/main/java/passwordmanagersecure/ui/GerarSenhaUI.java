package passwordmanagersecure.ui;

import passwordmanagersecure.utils.PasswordGenerator;

import javax.swing.*;
import java.awt.*;

public class GerarSenhaUI extends JFrame {
    public GerarSenhaUI(JFrame parent) {
        setTitle("Gerar Senha Segura");
        setSize(400, 200);
        setLocationRelativeTo(parent); // ajustado para abrir centrado na janela pai

        JLabel label = new JLabel("Senha Gerada:", SwingConstants.CENTER);
        JTextField senhaField = new JTextField();
        senhaField.setEditable(false);

        JButton gerarButton = new JButton("Gerar");
        JButton voltarButton = new JButton("Voltar");

        gerarButton.addActionListener(e -> {
            String senha = PasswordGenerator.generateStrongPassword(12);
            senhaField.setText(senha);
        });

        voltarButton.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        JPanel painel = new JPanel(new GridLayout(4, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painel.add(label);
        painel.add(senhaField);
        painel.add(gerarButton);
        painel.add(voltarButton);

        add(painel);
        setVisible(true);
    }
}
