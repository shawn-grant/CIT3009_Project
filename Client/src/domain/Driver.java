package domain;

import java.awt.Color;

import javax.swing.UIManager;

import view.screens.MainScreen;

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