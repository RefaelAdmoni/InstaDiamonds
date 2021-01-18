package com.example.instadiamond;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instadiamond.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements ProductsListFragment.Delegate {
    NavController navCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //controlling the nav bar
        navCtrl = Navigation.findNavController(this,R.id.home_nav_host);
        NavigationUI.setupActionBarWithNavController(this,navCtrl);

//        //bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.home_bottom_nav);
        NavigationUI.setupWithNavController(bottomNav,navCtrl);
    }

    @Override
    public void onItemSelected(Product product) {
        navCtrl = Navigation.findNavController(this,R.id.home_nav_host);
//        navCtrl.navigate(R.id.action_productsListFragment_to_productDetailsFragment);

        //forward args
//        ProductsListFragmentDirections.ActionProductsListFragmentToProductDetailsFragment directions = ProductsListFragmentDirections.actionProductsListFragmentToProductDetailsFragment(product);
//        navCtrl.navigate(directions);

        //send args to global fragment in nav_graph
        NavGraphDirections.ActionGlobalProductDetailsFragment direction = ProductsListFragmentDirections.actionGlobalProductDetailsFragment(product);
        navCtrl.navigate(direction);
    }

    //control the action bar btn (add)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
   //     getMenuInflater().inflate(R.menu.products_list_menu,menu);
        return true;
    }

    //@Override
    //public boolean onOptionItemSelected(MenuItem item) {
     //   return false;
   // }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:                     //the btn back in nav bar menu calls 'android.R.id.home'
                navCtrl.navigateUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}