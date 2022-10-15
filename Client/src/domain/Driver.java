package domain;

import client.Client;
import models.Product;
import view.MainScreen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Client client = new Client();
        client.sendAction("Add Product");
        client.sendProduct(new Product("JUC", "Juice", "Sweet", "Tasty liquids",
                15, 70.0f));
        client.receiveResponse();
        client.closeConnections();*/
        new MainScreen();

        Object obj = addProducts();
        List<Product> productList = (ArrayList<Product>) obj;

        for(Product product: productList) {
            System.out.println(product.getName());
        }
    }

    private static List<Product> addProducts() {
        Product product = new Product("HSD", "Bulla", "Bulla sweet", "Sweetest food you'll eat", 20, 78.0f);
        Product product2 = new Product("HSD", "Bun", "Bulla sweet",
                "Sweetest food you'll eat", 20, 78.0f);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        return productList;
    }
}