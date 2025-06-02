package passwordmanagersecure;

import passwordmanagersecure.firebase.FirebaseUtil;
import passwordmanagersecure.ui.LoginUI;

public class Main {

    public static void main(String[] args) {
        try {
            // Inicializa Firebase (certifique-se de que sua classe faz isso corretamente)
            FirebaseUtil.initialize();

            // Inicia a GUI na Event Dispatch Thread (requisito do Swing)
            javax.swing.SwingUtilities.invokeLater(() -> {
                new LoginUI();
            });

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o app: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
