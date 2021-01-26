package com.example.instadiamond;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.instadiamond.model.Product;


public class EditProductFragment extends Fragment {

    Product product;
    EditText name;
    EditText carats;
    EditText price;
    EditText urlImage;
    Button updateBtn;


    public EditProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_product, container, false);

        name = view.findViewById(R.id.nameProduct_et_EditProductFragment);
        carats = view.findViewById(R.id.caratProduct_et_EditProductFragment);
        price = view.findViewById(R.id.priceProduct_et_EditProductFragment);
        //urlImage = view.findViewById(R.id.imageUrlProduct_et_EditProductFragment2);
        updateBtn = view.findViewById(R.id.updateBtn_editProductFragment);


        //getting all data from last fragment, and define the product.
        product = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProduct();

        //connect between the local parameters and getArguments() from last screen.
        name.setText(product.getName__Product());
        carats.setText(product.getCarat__Product()+"");
        price.setText(product.getPrice__Product()+"");
        //urlImage.setText(product.getImageUrl__Product());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validation
            }
        });

        return view;
    }

    public void UpdateProduct() {

    }
}