/**
 * MainScreen.java
 * A tabbed layout to switch between diffrent sections of the app
 * Author (s): Shawn Grant, Malik Heron
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainScreen extends JFrame implements ActionListener {

    private JPanel panel;
    private JTextArea textArea;
    private JLabel statusLabel, requestsLabel;
    private JTextField statusField, requestsField;
    private JButton stopButton, exitButton;
    private final ServerSocket serverSocket;

    public MainScreen(ServerSocket serverSocket) {
        super("Jan's Wholesale and Retail - Server");
        this.serverSocket = serverSocket;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        // label properties
        statusLabel = new JLabel("Server status: ");
        statusLabel.setFont(new Font("arial", Font.BOLD, 15));
        statusLabel.setPreferredSize(new Dimension(130, 20));

        requestsLabel = new JLabel("Requests received: ");
        requestsLabel.setFont(new Font("arial", Font.BOLD, 15));
        requestsLabel.setPreferredSize(new Dimension(150, 20));

        // text area properties
        textArea = new JTextArea();
        textArea.setFont(new Font("arial", Font.PLAIN, 14));
        textArea.setPreferredSize(new Dimension(500, 360));
        textArea.setEditable(false);

        // field properties
        statusField = new JTextField("Running");
        statusField.setHorizontalAlignment(JTextField.CENTER);
        statusField.setFont(new Font("arial", Font.PLAIN, 14));
        statusField.setPreferredSize(new Dimension(90, 30));
        statusField.setEditable(false);

        requestsField = new JTextField("0");
        requestsField.setHorizontalAlignment(JTextField.CENTER);
        requestsField.setFont(new Font("arial", Font.PLAIN, 14));
        requestsField.setPreferredSize(new Dimension(60, 30));
        requestsField.setEditable(false);

        // button properties
        stopButton = new JButton("STOP SERVER");
        stopButton.setPreferredSize(new Dimension(140, 30));
        stopButton.setForeground(Color.BLUE);
        stopButton.setFont(new Font("arial", Font.BOLD, 12));
        stopButton.setFocusPainted(false);

        exitButton = new JButton("EXIT");
        exitButton.setPreferredSize(new Dimension(140, 30));
        exitButton.setForeground(Color.BLUE);
        exitButton.setFont(new Font("arial", Font.BOLD, 12));
        exitButton.setFocusPainted(false);
        exitButton.setVisible(false);

        // Panel properties
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setSize(530, 500);
    }

    private void addComponentsToPanels() {
        panel.add(statusLabel);
        panel.add(statusField);
        panel.add(requestsLabel);
        panel.add(requestsField);
        panel.add(stopButton);
        panel.add(exitButton);
        panel.add(new JScrollPane(textArea));
    }

    private void addPanelsToWindow() {
        add(panel);
    }

    private void setWindowProperties() {
        setLayout(null);
        setSize(530, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void registerListeners() {
        stopButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    public void setRequestsField(int requestAmount) {
        requestsField.setText(String.valueOf(requestAmount));
    }

    public void setTextArea(String text) {
        textArea.append(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(stopButton)) {
            int selection = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure?",
                    "Stop Server",
                    JOptionPane.YES_NO_OPTION
            );

            if (selection == JOptionPane.YES_OPTION) {
                try {
                    serverSocket.close();
                    statusField.setText("Stopped");
                    exitButton.setVisible(true);
                    stopButton.setEnabled(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (e.getSource().equals(exitButton)) {
            int selection = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION
            );

            if (selection == JOptionPane.YES_OPTION)
                System.exit(0);
        }
    }
}
