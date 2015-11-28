package controle;

import gui.TelaPrincipal;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        setSystemLookAndFeel();
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }

    private static void setSystemLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
