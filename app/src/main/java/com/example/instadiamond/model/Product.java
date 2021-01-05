package com.example.instadiamond.model;

import java.io.Serializable;

public class Product implements Serializable {
   public String id__Product;             //product id - on background.
   public String name__Product;            //what is the name of product?
   public float Carat_Diamond__Product;    //how many carats of the diamond?
   public float weight_Diamond__Product;   //what is the diamond weight?
   public String color_Diamond__product;   //what is the diamond color?
   public Boolean isAJewel__Product;       //the product is a jewel or only diamond
   public Boolean isChecked__Product;        //is the product selected?
   public float price__Product;            //price per 1 CT
   public String imageUrl__Product;        //path the image product

    public String sellerId__Product;



    Product(String name, String id){
       this.name__Product = name;
       this.id__Product = id;
       this.isChecked__Product = false;
    }

}
