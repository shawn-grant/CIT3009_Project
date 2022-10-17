package domain;

import view.MainScreen;

import javax.swing.UIManager;
import java.awt.Color;

public class Driver {
    public static void main(String[] args) {
        try {
            UIManager.put("ToggleButton.select", new Color(0, 0, 119));
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainScreen();
    }
}