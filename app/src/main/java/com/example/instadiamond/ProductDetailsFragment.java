package com.example.instadiamond;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.instadiamond.model.Product;
import com.google.firebase.auth.FirebaseAuth;

public class ProductDetailsFragment extends Fragment {
    private Product product;
    TextView name;
    TextView carat;
    Button closeBtn, editBtn, deleteBtn;
    FirebaseAuth firebaseAuth;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_details, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        name = view.findViewById(R.id.product_details_name_tv);
        carat = view.findViewById(R.id.product_details_carats_tv);

        //getting all data from last fragment, and define the product.
        product = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProduct();
        if (product != null){           //option check again
            update_dispaly();
        }

        deleteBtn = view.findViewById(R.id.delete_btn_productDetailsFragment);
        editBtn = view.findViewById(R.id.Edit_btn_productDetailsFragment);

        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("TAG","firebase uid = " + firebaseAuth.getCurrentUser().getUid());
        Log.d("TAG","product uid = " + product.getSellerId__Product());
        if (!firebaseAuth.getCurrentUser().getUid().equals(product.getSellerId__Product())) {
            deleteBtn.setVisibility(view.GONE);
            editBtn.setVisibility(view.GONE);

        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.home_nav_host);
                NavDirections direction = EditProductFragmentDirections.actionGlobalEditProductFragment(product);
                navController.navigate(direction);
            }
        });

        //defined the close btn
        closeBtn = view.findViewById(R.id.product_details_close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navCtrl = Navigation.findNavController(v);
                navCtrl.popBackStack();
            }
        });
        return view;
    }

    private void update_dispaly() {
        name.setText(product.name__Product);
        carat.setText(product.carat__Product +"");
    }
}