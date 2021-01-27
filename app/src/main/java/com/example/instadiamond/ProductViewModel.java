package com.example.instadiamond;

import androidx.lifecycle.ViewModel;

import com.example.instadiamond.model.Product;
import com.example.instadiamond.model.ProductModel;

public class ProductViewModel extends ViewModel {
    public void add(Product product, ProductModel.Listener listener) {
        ProductModel.instance.addProduct(product, listener);
    }

    public void update(Product product, ProductModel.Listener listener) {
        ProductModel.instance.updateProduct(product, listener);
    }

    public void delete(Product product, ProductModel.Listener listener) {
        //ProductModel.instance.deleteProduct(product, listener);
    }
}
