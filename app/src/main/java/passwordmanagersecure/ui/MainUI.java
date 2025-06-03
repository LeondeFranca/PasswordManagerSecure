package passwordmanagersecure.ui;

import passwordmanagersecure.utils.UserSession;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    public MainUI() {
        setTitle("Gerenciador de Senhas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Gerenciador de Senhas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton cadastrarButton = new JButton("Cadastrar Nova Senha");
        JButton listarButton = new JButton("Listar Senhas");
        JButton excluirButton = new JButton("Excluir Senha");
        JButton gerarButton = new JButton("Gerar Senha Segura");
        JButton verificarButton = new JButton("Verificar Senha Vazada");
        JButton sairButton = new JButton("Sair");

        cadastrarButton.addActionListener(e -> {
            new CadastroUI(this);
            this.setVisible(false);
        });

        listarButton.addActionListener(e -> {
            new ListarSenhasUI(this);
            this.setVisible(false);
        });

        excluirButton.addActionListener(e -> {
            new ExcluirUI(this);
            this.setVisible(false);
        });

        gerarButton.addActionListener(e -> {
            new GerarSenhaUI(this);
            this.setVisible(false);
        });

        verificarButton.addActionListener(e -> {
            new VerificarVazadaUI(this);
            this.setVisible(false);
        });

        sairButton.addActionListener(e -> {
            UserSession.logout();
            new LoginUI();
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(listarButton);
        buttonPanel.add(excluirButton);
        buttonPanel.add(gerarButton);
        buttonPanel.add(verificarButton);
        buttonPanel.add(sairButton);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
