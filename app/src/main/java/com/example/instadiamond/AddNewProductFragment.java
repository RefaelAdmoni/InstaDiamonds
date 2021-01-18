package com.example.instadiamond;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
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

import java.util.Date;


public class AddNewProductFragment extends Fragment {

    View view;
    ImageView imageView;
    TextView idTv;
    TextView nameTv;
    TextView typeTv;
    TextView costTv;
    CheckBox checkBox;
    Button takePhotoBtn;
    Button saveBtn;
    Bitmap imageBitmap;
    ProgressBar progressBar;
    ProductListViewModel viewModel;


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

        View view = inflater.inflate(R.layout.fragment_add_new_product, container, false);

//        takePhotoBtn = view.findViewById(R.id.new_jewelry_take_photo_btn);
//        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePhoto();
//            }
//        });

        nameTv = view.findViewById(R.id.addproduct_name_et);
        idTv = view.findViewById(R.id.addproduct_id_et);
        checkBox = view.findViewById(R.id.addproduct_cb_cb);
        //imageView = view.findViewById(R.id.new_jewelry_image_v);
        saveBtn = view.findViewById(R.id.addproduct_save_btn);

        viewModel = new ViewModelProvider(this).get(ProductListViewModel.class);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTv.getText().toString().trim();
                String ID = idTv.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameTv.setError("Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(ID)) {
                    idTv.setError("ID is required.");
                    return;
                }

                saveNewJewelry();
            }
        });

        return view;

    }

    void saveNewJewelry() {
        //progressBar.setVisibility(View.VISIBLE);

        final String name = nameTv.getText().toString();

        final String id = idTv.getText().toString();

        final Boolean checked = checkBox.isChecked();


        java.util.Date d = new Date();

        Product product = new Product(name,checked);
        ProductFirebase.addProduct(product, null);
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