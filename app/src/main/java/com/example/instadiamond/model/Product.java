package com.example.instadiamond.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;

    public String name__Product;            //what is the name of product?
    public String carat__Product;           //how many carats of the diamond?
    public Boolean isChecked__Product;      //is the product selected?
    public String price__Product;           //price per 1 CT
    public String imageUrl__Product;        //path the image product

    public Boolean isDeleted;               //is this product was delete?
    public String sellerId__Product;

//    public long lastUpdated;


    public Product() {
        this.id                     = "temp";
        this.name__Product          = "empty";
        this.carat__Product         = "0";
        this.isChecked__Product     = false;
        this.price__Product         = "0";
        this.imageUrl__Product      = "0";
        this.isDeleted              = false;
    }

    public Product(String id, String name, String carat, String price, String imageUrl, Boolean isChecked, String pid) {
        this.id                     = id;
        this.name__Product          = name;
        this.carat__Product         = carat;
        this.isChecked__Product     = isChecked;
        this.price__Product         = price;
        this.imageUrl__Product      = imageUrl;
        this.isDeleted              = false;
        this.sellerId__Product      = pid;
    }

    @NonNull
    public String getId__Product() {
        return id;
    }

    public void setId__Product(@NonNull String id) {
        this.id = id;
    }

    public String getName__Product() {
        return name__Product;
    }

    public void setName__Product(String name__Product) {
        this.name__Product = name__Product;
    }

    public String getCarat__Product() {
        return carat__Product;
    }

    public void setCarat__Product(String carat__Product) {
        this.carat__Product = carat__Product;
    }

    public Boolean getChecked__Product() {
        return isChecked__Product;
    }

    public void setChecked__Product(Boolean checked__Product) {
        isChecked__Product = checked__Product;
    }

    public String getPrice__Product() {
        return price__Product;
    }

    public void setPrice__Product(String price__Product) {
        this.price__Product = price__Product;
    }

    public String getImageUrl__Product() {
        return imageUrl__Product;
    }

    public void setImageUrl__Product(String imageUrl__Product) {
        this.imageUrl__Product = imageUrl__Product;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getSellerId__Product() {
        return sellerId__Product;
    }

    public void setSellerId__Product(String sellerId__Product) {
        this.sellerId__Product = sellerId__Product;
    }

}
