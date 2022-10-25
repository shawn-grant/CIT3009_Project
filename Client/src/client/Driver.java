package client;

import java.awt.Color;
import java.util.List;

import javax.swing.UIManager;

import models.Invoice;
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

    private static void getInvoice() {
        Client client = new Client();
        client.sendAction("View Invoice");
        client.sendInvoiceNumber("19525");
        List<Invoice> invoiceList = client.receiveViewInvoiceResponse();
        client.closeConnections();
        System.out.println(invoiceList);

        Invoice invoice = invoiceList.get(0);
        System.out.println("Invoice Number: " + invoice.getId().getInvoiceNumber());
        System.out.println("Billing date: " + invoice.getId().getBillingDate());
        System.out.println("Customer ID: " + invoice.getCustomerId());
        System.out.println("Employee ID: " + invoice.getEmployeeId());
        System.out.println("Total Cost: " + invoice.getTotalCost());
        System.out.println();

        for(Invoice inv: invoiceList) {
            System.out.println("Invoice Number: " + inv.getId().getInvoiceNumber());
            System.out.println("Item Name: " + inv.getId().getItemName());
            System.out.println("Billing date: " + inv.getId().getBillingDate());
            System.out.println("Unit Price: " + inv.getUnitPrice());
            System.out.println("Quantity: " + inv.getQuantity());
            System.out.println("Customer ID: " + inv.getCustomerId());
            System.out.println("Employee ID: " + inv.getEmployeeId());
            System.out.println("Item total Cost: " + (inv.getQuantity() * inv.getUnitPrice()));
            System.out.println();
        }
    }
}