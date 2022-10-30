package client;

import java.awt.Color;
import java.util.List;

import javax.swing.UIManager;

import models.Invoice;
import models.InvoiceItem;
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