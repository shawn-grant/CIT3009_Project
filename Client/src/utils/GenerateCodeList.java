package utils;

import java.util.List;

import client.Client;
import models.Product;

public class GenerateCodeList {
	
	 // retrieve all product information
    public String[] getCodes() {
    	Client client = new Client();
        client.sendAction("View Inventory");
        List<Product> productList = client.receiveViewInventoryResponse();
        client.closeConnections();
        String codes = " ";
        String[] list = {""};
        for (Product product : productList) {
        	codes += (product.getCode() + " ");
        }  
        list = codes.split(" ");
        return list;
        
    }
        
}
