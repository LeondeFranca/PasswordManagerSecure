package passwordmanagersecure.ui;

import passwordmanagersecure.firebase.FirebaseUtil;
import passwordmanagersecure.models.Credential;
import passwordmanagersecure.utils.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListarSenhasUI extends JFrame {

    public ListarSenhasUI(JFrame parent) {
        setTitle("Minhas Senhas");
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"Serviço", "Usuário", "Senha"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        try {
            List<Credential> lista = FirebaseUtil.getCredentials(Session.getCurrentUserId());
            for (Credential cred : lista) {
                tableModel.addRow(new Object[]{
                    cred.getService(),
                    cred.getUsername(),
                    cred.getDecryptedPassword()
                });
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar credenciais.");
        }

        JButton voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(voltarBtn);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
