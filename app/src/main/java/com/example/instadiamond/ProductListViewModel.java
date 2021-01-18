package com.example.instadiamond;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.instadiamond.model.Product;
import com.example.instadiamond.model.ProductFirebase;
import com.example.instadiamond.model.ProductModel;

import java.util.LinkedList;
import java.util.List;

public class ProductListViewModel extends ViewModel {
    LiveData<List<Product>> liveData;

    public LiveData<List<Product>> getData(){
        if (liveData == null) {
            liveData = ProductModel.instance.getAllProducts();
        }
        return liveData;
    }

    public void refresh(ProductModel.CompListener listener) {
        ProductModel.instance.refreshProductList(listener);
    }
}
