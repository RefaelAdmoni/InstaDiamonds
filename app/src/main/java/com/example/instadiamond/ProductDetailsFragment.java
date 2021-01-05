package com.example.instadiamond;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.instadiamond.model.Product;

public class ProductDetailsFragment extends Fragment {
    private Product product;
    TextView name;
    TextView carats;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_details, container, false);

        name = view.findViewById(R.id.product_details_name_tv);
        carats = view.findViewById(R.id.product_details_carats_tv);

        //getting all data from last fragment, and define the product.
        product = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProduct();
        if (product != null){           //option check again
            update_dispaly();
        }

        //defined the close btn
        View closeBtn = view.findViewById(R.id.product_details_close_btn);
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
        carats.setText(product.Carat_Diamond__Product +"");
    }
}