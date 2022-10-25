package utils;

import java.util.List;

import client.Client;
import models.Product;

public class GenerateCodeList {
	
	 // retrieve all product information
    public String[] getCodes() {
    	Client client = new Client();
        client.sendAction("View Products");
        List<Product> productList = client.receiveViewProductsResponse();
        client.closeConnections();
        StringBuilder codes = new StringBuilder(" ");
        String[] list = {""};
        for (Product product : productList) {
        	codes.append(product.getCode()).append(" ");
        }
        list = codes.toString().split(" ");
        return list;

    }

}
