package server;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Driver {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        new Server();
    }
}
