package domain;

import view.MainScreen;

import javax.swing.*;

public class Driver {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainScreen();

        /*Object obj = addProducts();
        List<Product> productList = (ArrayList<Product>) obj;

        for(Product product: productList) {
            System.out.println(product.getName());
        }*/
    }

    /*private static List<Product> addProducts() {
        Product product = new Product("HSD", "Bulla", "Bulla sweet",
                "Sweetest food you'll eat", 20, 78.0f);
        Product product2 = new Product("HSD", "Bun", "Bulla sweet",
                "Sweetest food you'll eat", 20, 78.0f);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        return productList;
    }*/
}