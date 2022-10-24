package utils;

import java.util.ArrayList;
import java.util.Arrays;
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
        
        for (Product product : productList) {
        	codes += (product.getCode() + " ");
        }
        
        String[] list = {""}; 
        list = codes.split(" ");
        System.out.println("Values in list: "+ Arrays.toString(list));
        return list;
        
    }
        
}
