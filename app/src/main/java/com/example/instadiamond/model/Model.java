package com.example.instadiamond.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    List<Product> products = new LinkedList<>();                //Boot list.
    public static final Model instance = new Model();          //singleton mode - for create always about the same objects.

    private Model(){
        for (int i=0; i<10;i++){
            Product prd = new Product("Kuki "+i,""+ i);
                    products.add(prd);
        }

    }

    public List<Product> getAllProducts(){
        return products;
    }

    Product get_Product(String id){
        return null;
    }

    void update_Product(Product product){

    }

    void add_Product(Product product){

    }

    void delete_Product(String id){

    }
}
