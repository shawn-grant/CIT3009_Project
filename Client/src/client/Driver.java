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
        //getInvoice();
    }

    private static void getInvoice() {
        //Get list of invoices
        Client client = new Client();
        client.sendAction("View Invoices");
        List<Invoice> invoiceList = client.receiveViewInvoiceResponse();
        client.closeConnections();
        System.out.println(invoiceList);

        //Find an invoice
        Client client3 = new Client();
        client3.sendAction("Find Invoice");
        client3.sendInvoiceNumber(16646);
        Invoice invoice2 = client3.receiveFindInvoiceResponse();
        client3.closeConnections();

        System.out.println("Invoice Number: " + invoice2.getInvoice_number());
        System.out.println("Billing date: " + invoice2.getBillingDate());
        System.out.println("Customer ID: " + invoice2.getCustomerId());
        System.out.println("Employee ID: " + invoice2.getEmployeeId());
        System.out.println("Total Cost: " + invoice2.getTotalCost());
        System.out.println();

        //Get invoice items
        Client client2 = new Client();
        client2.sendAction("View Invoice Item");
        client2.sendInvoiceNumber(16646);
        List<InvoiceItem> invoiceItemList = client2.receiveViewInvoiceItemResponse();
        client2.closeConnections();
        System.out.println(invoiceItemList);

        for(InvoiceItem inv: invoiceItemList) {
            System.out.println("Invoice Number: " + inv.getId().getInvoiceNumber());
            System.out.println("Item Name: " + inv.getId().getItemName());
            System.out.println("Unit Price: " + inv.getUnitPrice());
            System.out.println("Quantity: " + inv.getQuantity());
            System.out.println("Item total Cost: " + (inv.getQuantity() * inv.getUnitPrice()));
            System.out.println();
        }

        //Delete an invoice
        Client client4 = new Client();
        client4.sendAction("Remove Invoice");
        client4.sendInvoiceNumber(16646);
        client4.receiveResponse();
        client4.closeConnections();
    }
}