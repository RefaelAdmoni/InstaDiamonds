package com.example.instadiamond;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.instadiamond.model.Product;
import com.example.instadiamond.model.ProductFirebase;
import com.example.instadiamond.model.ProductModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class AddNewProductFragment extends Fragment {

    View view;
    TextView nameTv;
    TextView imageUrl_Tv;
    //    TextView idTv;
    TextView caratTv;
    TextView priceTv;
//    CheckBox checkBox;
    Button takePhotoBtn;
    Bitmap imageBitmap;
    ProgressBar progressBar;
    Button saveBtn;
    ProductViewModel viewModel;


    public AddNewProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_new_product, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();

//        takePhotoBtn = view.findViewById(R.id.new_jewelry_take_photo_btn);
//        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePhoto();
//            }
//        });

        nameTv = view.findViewById(R.id.addproduct_name_et);
        caratTv = view.findViewById(R.id.addproduct_carat_et);
        priceTv = view.findViewById(R.id.addproduct_price_et);
        imageUrl_Tv = view.findViewById(R.id.addproduct_imageUrl_et);
        saveBtn = view.findViewById(R.id.addproduct_save_btn);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTv.getText().toString().trim();
                String carat = caratTv.getText().toString().trim();
                String price = priceTv.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameTv.setError("Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(carat)) {
                    caratTv.setError("Carats is required.");
                    return;
                }

                if (TextUtils.isEmpty(price)) {
                    priceTv.setError("price is required.");
                    return;
                }

                saveNewProduct();
            }
        });

        return view;

    }

    void saveNewProduct() {
        //progressBar.setVisibility(View.VISIBLE);

        final String name = nameTv.getText().toString();
        final String carat = caratTv.getText().toString();
        final String price = priceTv.getText().toString();
        final String imageUrl = imageUrl_Tv.getText().toString();

//        final Boolean checked = false;


        java.util.Date d = new Date();

        Product product = new Product(name,carat,price,imageUrl,false);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            product.setSellerId__Product(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }

        viewModel.add(product, new ProductModel.Listener<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                Log.d("TAG", "save new product success");
                NavController navController = Navigation.findNavController(view);
                // Back to list
                navController.navigateUp();
            }
        });


//        NavController navController = Navigation.findNavController(getView());
//        navController.navigateUp();


//        viewModel.add(jewelry, new JewelryModel.Listener<Boolean>() {
//            @Override
//            public void onComplete(Boolean data) {
//                Log.d("TAG", "save new jewelry success");
//                NavController navController = Navigation.findNavController(view);
//                // Back to list
//                navController.navigateUp();
//            }
//        });

//        if (imageBitmap != null) {
//            StorageModel.uploadImage(imageBitmap, "my_photo" + d.getTime(), new StorageModel.Listener() {
//                @Override
//                public void onSuccess(String url) {
//                    Log.d("TAG", "image url = " + url);
//
//                    // create object
//                    Jewelry jewelry = new Jewelry(name,name,type,cost,ifSold,url);
//                    viewModel.add(jewelry, new JewelryModel.Listener<Boolean>() {
//                        @Override
//                        public void onComplete(Boolean data) {
//                            Log.d("TAG", "save new jewelry success");
//                            NavController navController = Navigation.findNavController(view);
//                            NavDirections direction = JewelryListFragmentDirections.actionGlobalJewelryListFragment();
//                            navController.navigate(direction);
//                        }
//                    });
//                }
//
//                @Override
//                public void onFail() {
//                    Log.d("TAG", "image url upload failed ");
//                }
//            });
//        } else {
//            // create object
//            Jewelry jewelry = new Jewelry(name,name,type,cost,ifSold,null);
//            viewModel.add(jewelry, new JewelryModel.Listener<Boolean>() {
//                @Override
//                public void onComplete(Boolean data) {
//                    Log.d("TAG", "save new jewelry success");
//                    NavController navController = Navigation.findNavController(view);
//                    // Back to list
//                    navController.navigateUp();
//                }
//            });
//        }


    }
}