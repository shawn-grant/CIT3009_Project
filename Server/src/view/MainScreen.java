/**
 * MainScreen.java
 * Main Screen for viewing server details
 * Author (s): Malik Heron
 */
package view;

import factories.SessionFactoryBuilder;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

public class MainScreen extends JFrame implements ActionListener {

    private final ServerSocket serverSocket;
    private JPanel panel;
    private JTextArea textArea;
    private JLabel statusLabel, requestsLabel;
    private JLabel statusText, requestsText;
    private JButton stopButton, exitButton;
    private JScrollPane scrollPane;

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
        statusLabel.setPreferredSize(new Dimension(105, 20));

        requestsLabel = new JLabel("Requests received: ");
        requestsLabel.setFont(new Font("arial", Font.BOLD, 15));
        requestsLabel.setPreferredSize(new Dimension(145, 20));

        statusText = new JLabel("Running");
        statusText.setFont(new Font("arial", Font.PLAIN, 14));
        statusText.setForeground(new Color(44, 124, 24));
        statusText.setPreferredSize(new Dimension(70, 30));

        requestsText = new JLabel("0");
        requestsText.setFont(new Font("arial", Font.PLAIN, 14));
        requestsText.setPreferredSize(new Dimension(60, 30));

        // text area properties
        textArea = new JTextArea();
        textArea.setFont(new Font("arial", Font.PLAIN, 14));
        textArea.setEditable(false);

        // button properties
        stopButton = new JButton("STOP SERVER");
        stopButton.setPreferredSize(new Dimension(140, 30));
        stopButton.setForeground(Color.RED);
        stopButton.setFont(new Font("arial", Font.BOLD, 12));
        stopButton.setFocusPainted(false);

        exitButton = new JButton("EXIT");
        exitButton.setPreferredSize(new Dimension(140, 30));
        exitButton.setForeground(Color.BLUE);
        exitButton.setFont(new Font("arial", Font.BOLD, 12));
        exitButton.setFocusPainted(false);
        exitButton.setVisible(false);

        // scrollPane properties
        scrollPane = new JScrollPane(
                textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setPreferredSize(new Dimension(505, 460));

        // Panel properties
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setSize(530, 600);
    }

    private void addComponentsToPanels() {
        panel.add(statusLabel);
        panel.add(statusText);
        panel.add(requestsLabel);
        panel.add(requestsText);
        panel.add(stopButton);
        panel.add(exitButton);
        panel.add(scrollPane);
    }

    private void addPanelsToWindow() {
        add(panel);
    }

    private void setWindowProperties() {
        setLayout(null);
        setSize(530, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
    }

    private void registerListeners() {
        stopButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    public void setRequestsText(int requestAmount) {
        requestsText.setText(String.valueOf(requestAmount));
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
                    SessionFactoryBuilder.closeSessionFactory();
                    statusText.setText("Stopped");
                    statusText.setForeground(Color.RED);
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
