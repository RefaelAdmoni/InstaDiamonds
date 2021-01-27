package com.example.instadiamond;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.instadiamond.model.Product;
import com.example.instadiamond.model.ProductModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class EditProductFragment extends Fragment {

    Product product;
    EditText name_ET;
    EditText carats_ET;
    EditText price_ET;
    EditText imageUrl_ET;
    Button updateBtn;
    ProductViewModel viewModel;
    View view;

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
        view = inflater.inflate(R.layout.fragment_edit_product, container, false);

        name_ET = view.findViewById(R.id.nameProduct_et_EditProductFragment);
        carats_ET = view.findViewById(R.id.caratProduct_et_EditProductFragment);
        price_ET = view.findViewById(R.id.priceProduct_et_EditProductFragment);
        //urlImage_ET = view.findViewById(R.id.imageUrlProduct_et_EditProductFragment2);
        updateBtn = view.findViewById(R.id.updateBtn_editProductFragment);


        //getting all data from last fragment, and define the product.
        product = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProduct();

        //connect between the local parameters and getArguments() from last screen.
        name_ET.setText(product.getName__Product());
        carats_ET.setText(product.getCarat__Product() + "");
        price_ET.setText(product.getPrice__Product() + "");
        //urlImage_ET.setText(product.getImageUrl__Product());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_ET.getText().toString().trim();
                String carat = carats_ET.getText().toString().trim();
                String price = price_ET.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    name_ET.setError("Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(carat)) {
                    carats_ET.setError("Carats is required.");
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    price_ET.setError("price is required.");
                    return;
                }

                updateProduct();
            }
        });

        return view;
    }

    public void updateProduct() {

        final String name = name_ET.getText().toString();
        final String carat = carats_ET.getText().toString();
        final String price = price_ET.getText().toString();
//        final String imageUrl = imageUrl_ET.getText().toString();

        java.util.Date d = new Date();

        product.setName__Product(name);
        product.setCarat__Product(carat);
        product.setPrice__Product(price);
//        product.setImageUrl__Product(imageUrl);


        viewModel.update(product, new ProductModel.Listener() {
            @Override
            public void onComplete(Object data) {
                Log.d("TAG", "update product success");
                NavController navController = Navigation.findNavController(view);
                // Back to list
                navController.navigateUp();
            }
        });

    }
}