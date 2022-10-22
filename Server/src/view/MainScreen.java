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
import java.awt.TrayIcon;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.AWTException;
import java.io.IOException;
import java.net.ServerSocket;

public class MainScreen extends JFrame implements ActionListener {

    private final ServerSocket serverSocket;
    private final Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/tray_icon.png"));
    private JPanel panel;
    private JTextArea textArea;
    private JLabel statusLabel, requestsLabel;
    private JLabel statusText, requestsText;
    private JButton stopButton, exitButton;
    private JScrollPane scrollPane;
    private MenuItem menuOpen, menuClose;
    private TrayIcon trayIcon;
    private PopupMenu popupMenu;

    public MainScreen(ServerSocket serverSocket) {
        super("Jan's Wholesale and Retail - Server");
        this.serverSocket = serverSocket;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        initializeComponents();
        addMenuItemsToPopupMenu();
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

        //menuItem properties
        menuOpen = new MenuItem("Open");
        menuOpen.setFont(new Font("system", Font.PLAIN, 14));
        menuClose = new MenuItem("Close");
        menuClose.setFont(new Font("system", Font.PLAIN, 14));

        //trayIcon and popupMenu properties
        popupMenu = new PopupMenu();
        trayIcon = new TrayIcon(image, "Jan's W&RMS - Server");
        trayIcon.setPopupMenu(popupMenu);
        trayIcon.setImageAutoSize(true);

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

    private void addMenuItemsToPopupMenu() {
        popupMenu.add(menuOpen);
        popupMenu.add(menuClose);
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
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
    }

    private void registerListeners() {
        menuOpen.addActionListener(this);
        menuClose.addActionListener(this);
        stopButton.addActionListener(this);
        exitButton.addActionListener(this);
        trayIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() >= 2){
                    SystemTray.getSystemTray().remove(trayIcon);
                    setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    SystemTray.getSystemTray().add(trayIcon);
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }

    public void setRequestsText(int requestAmount) {
        requestsText.setText(String.valueOf(requestAmount));
    }

    public void setTextArea(String text) {
        textArea.append(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(menuOpen)) {
            SystemTray.getSystemTray().remove(trayIcon);
            setVisible(true);
        }
        if (e.getSource().equals(menuClose)) {
            SystemTray.getSystemTray().remove(trayIcon);
            System.exit(0);
        }
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
