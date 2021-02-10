package com.example.instadiamond;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.provider.MediaStore;
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
import com.example.instadiamond.model.StoreModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;


public class AddNewProductFragment extends Fragment {
    final static String PRODUCT_COLLECTION = "products";

    Product newProduct;
    View view;
    TextView nameTv;
    TextView imageUrl_Tv;
    //    TextView idTv;
    TextView caratTv;
    TextView priceTv;
    //    CheckBox checkBox;
    Button takePhotoBtn;
    Boolean btnTakePhoto_isClicked;
    Bitmap imageBitmap;
    ProgressBar progressBar;
    Button saveBtn;
    ProductViewModel viewModel;
    ImageView imageView;

    public AddNewProductFragment() {  // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnTakePhoto_isClicked = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_new_product, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();

        //take a photo from camera
        takePhotoBtn = view.findViewById(R.id.takePhoto_Btn_addNewProductFragment);
        btnTakePhoto_isClicked = false;
        takePhotoBtn.setOnClickListener(v -> {
            btnTakePhoto_isClicked = true;
            takePhoto();
        });

        imageView = view.findViewById(R.id.image_imageView_addProductFragment);


        nameTv = view.findViewById(R.id.addproduct_name_et);
        caratTv = view.findViewById(R.id.addproduct_carat_et);
        priceTv = view.findViewById(R.id.addproduct_price_et);
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

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void takePhoto() {
        Log.d("TAG", "Take photo clicked");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            Log.d("TAG", "Start activity - take picture intent");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // callback after camera
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = rotateImage((Bitmap) extras.get("data"));
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public static Bitmap rotateImage(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    void saveNewProduct() {
        //progressBar.setVisibility(View.VISIBLE);
//        newProduct = new Product();
        final String name = nameTv.getText().toString();
        final String carat = caratTv.getText().toString();
        final String price = priceTv.getText().toString();
//        final String imageUrl = imageUrl_Tv.getText().toString();

//        final Boolean checked = false;


        if (!btnTakePhoto_isClicked) {
            imageView.setImageResource(R.drawable.diamond_ring);
        }


        java.util.Date d = new Date();
//        if (newProduct.imageUrl__Product.equals("")) {
            StoreModel.uploadImage(imageBitmap, "my_photo_" + d.getTime(), new StoreModel.Listener() {
                String uid;

                @Override
                public void onSuccess(String url) {
                    Log.d("TAG", "url: " + url);
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        uid = (FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }

                    newProduct = new Product("temp", name, carat, price, url, false, uid);


                    ProductModel.instance.addProduct(newProduct, new ProductModel.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            NavController navCtrl = Navigation.findNavController(view);
                            NavDirections direction = ProductsListFragmentDirections.actionGlobalProductsListFragment();
                            navCtrl.navigate(direction);
                        }
                    });
                }

                @Override
                public void onFail() {
                }
            });
//        }




//        viewModel.add(newProduct, new ProductModel.Listener<Boolean>() {
//            @Override
//            public void onComplete(Boolean data) {
//                Log.d("TAG", "save new product success");
//                NavController navController = Navigation.findNavController(view);
//                // Back to list
//                navController.navigateUp();
//            }
//        });


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