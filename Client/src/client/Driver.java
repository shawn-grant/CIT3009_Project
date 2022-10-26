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
        client = new Client();
        client.sendAction("Find Invoice");
        client.sendInvoiceNumber(41996);
        Invoice invoice = client.receiveFindInvoiceResponse();
        client.closeConnections();

        System.out.println("Invoice Number: " + invoice.getInvoice_number());
        System.out.println("Billing date: " + invoice.getBillingDate());
        System.out.println("Customer ID: " + invoice.getCustomerId());
        System.out.println("Employee ID: " + invoice.getEmployeeId());
        System.out.println("Total Cost: " + invoice.getTotalCost());
        System.out.println();

        //Get invoice items
        client = new Client();
        client.sendAction("View Invoice Item");
        client.sendInvoiceNumber(41996);
        List<InvoiceItem> invoiceItemList = client.receiveViewInvoiceItemResponse();
        client.closeConnections();
        System.out.println(invoiceItemList);

        for(InvoiceItem invoiceItem: invoiceItemList) {
            System.out.println("Invoice Number: " + invoiceItem.getId().getInvoiceNumber());
            System.out.println("Item Name: " + invoiceItem.getId().getItemName());
            System.out.println("Unit Price: " + invoiceItem.getUnitPrice());
            System.out.println("Quantity: " + invoiceItem.getQuantity());
            System.out.println("Item total Cost: " + (invoiceItem.getQuantity() * invoiceItem.getUnitPrice()));
            System.out.println();
        }

        //Delete an invoice
        client = new Client();
        client.sendAction("Remove Invoice");
        client.sendInvoiceNumber(16646);
        client.receiveResponse();
        client.closeConnections();
    }
}