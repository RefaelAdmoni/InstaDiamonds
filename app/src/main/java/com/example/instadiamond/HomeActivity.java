package com.example.instadiamond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.instadiamond.model.Product;

public class HomeActivity extends AppCompatActivity implements ProductsListFragment.Delgate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onItemSelected(Product product) {
        NavController navCtrl = Navigation.findNavController(this,R.id.home_nav_host);
//        navCtrl.navigate(R.id.action_productsListFragment_to_productDetailsFragment);

        //forword args
        ProductsListFragmentDirections.ActionProductsListFragmentToProductDetailsFragment directions = ProductsListFragmentDirections.actionProductsListFragmentToProductDetailsFragment(product);
        navCtrl.navigate(directions);

    }
}