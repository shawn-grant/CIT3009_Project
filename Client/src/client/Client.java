package client;

import models.Employee;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket connectionSocket;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private String action;

    public Client() {
        this.createConnection();
        this.configureStreams();
    }

    private void createConnection() {
        try {
            connectionSocket = new Socket("127.0.0.1",8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureStreams() {
        try {
            objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
            objIs = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        try {
            objOs.close();
            objIs.close();
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAction(String action) {
        this.action = action;
        try {
            objOs.writeObject(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmployee(Employee employee) {
        try {
            objOs.writeObject(employee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmployeeId(String empId) {
        try {
            objOs.writeObject(empId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveRequests() {
        try {
            if (action.equalsIgnoreCase("Add Employee")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(null, "Record added successfully", "Add Record Status",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (action.equalsIgnoreCase("Find Employee")) {
                Employee employee = new Employee();
                employee = (Employee) objIs.readObject();
                if (employee == null) {
                    JOptionPane.showMessageDialog(null, "Record could not be found", "Find Record Status",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
