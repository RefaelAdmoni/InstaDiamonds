package com.example.instadiamond.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;


public class ProductModel {
    public static final ProductModel instance = new ProductModel();

    public interface Listener<T> {

        void onComplete(T data);
    }

    public interface CompListener {
        void onComplete();
    }

    private ProductModel() {}


    public void refreshProductList(final CompListener listener) {
        ProductFirebase.getAllProducts(new Listener<List<Product>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Product> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (Product product : data) {
                            AppLocalDb.db.productDao().insertAll(product);
                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if (listener != null) listener.onComplete();
                    }
                }.execute("");
            }
        });
    }


    public LiveData<List<Product>> getAllProducts() {
        LiveData<List<Product>> liveData = AppLocalDb.db.productDao().getAll();
        refreshProductList(null);
        return liveData;
    }

    @SuppressLint("StaticFieldLeak")
    public void addProduct(final Product product, Listener<Boolean> listener) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.productDao().insertAll(product);
                return "";
            }
        }.execute("");
        ProductFirebase.addProduct(product, listener);
    }

    @SuppressLint("StaticFieldLeak")
    public void updateProduct(Product product, Listener listener) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.productDao().insertAll(product);
                return "";
            }
        }.execute("");
        ProductFirebase.updateProduct(product, listener);
    }

    @SuppressLint("StaticFieldLeak")
    public void delete_Product(Product product, Listener listener) {
        product.setIsDeleted(true);
        new AsyncTask<String, String, String>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.productDao().insertAll(product);              //changing the status object
                AppLocalDb.db.productDao().delete(product);                 //delete from local DB

                Log.d("TAG", "The item deleted successfully from local DB");
                return "";
            }
        }.execute("");
        ProductFirebase.deleteProduct(product, listener);                   //delete the item from DB server
    }
}
