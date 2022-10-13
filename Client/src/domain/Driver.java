package domain;

import client.Client;
import view.MainScreen;

import javax.swing.*;

public class Driver {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Client client = new Client();
        new MainScreen();
    }
}