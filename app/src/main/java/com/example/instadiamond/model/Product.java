package com.example.instadiamond.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
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

//    public long lastUpdated;

    @NonNull
    public String getId__Product() {
        return id__Product;
    }

    public Product() {}

    public Product(String id , String name){
       this.name__Product = name;
       this.id__Product = id;
       this.isChecked__Product = false;
    }

    public Product(String name, Boolean checked){
        this.name__Product = name;
        this.isChecked__Product = checked;
    }

    public Product(String id , String name, boolean checked){
        this.name__Product = name;
        this.id__Product = id;
        this.isChecked__Product = checked;
    }

    public void setId__Product(@NonNull String id__Product) {
        this.id__Product = id__Product;
    }

    public String getName__Product() {
        return name__Product;
    }

    public void setName__Product(String name__Product) {
        this.name__Product = name__Product;
    }

    public Boolean getChecked__Product() {
        return isChecked__Product;
    }

    public void setChecked__Product(Boolean checked__Product) {
        isChecked__Product = checked__Product;
    }

    public String getImageUrl__Product() {
        return imageUrl__Product;
    }

    public void setImageUrl__Product(String imageUrl__Product) {
        this.imageUrl__Product = imageUrl__Product;
    }

    //    public Map<String,Object> toMap() {
//        HashMap<String,Object> result = new HashMap<>();
//        result.put("id",id__Product);
//        result.put("name",name__Product);
//        result.put("imgUrl",imageUrl__Product);
//        result.put("isChecked",isChecked__Product);
//        result.put("lastUpdated", FieldValue.serverTimestamp());
//        return result;
//    }
}
