package com.example.instadiamond;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instadiamond.model.Product;
import com.example.instadiamond.model.ProductModel;
import com.example.instadiamond.model.StoreModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class EditProductFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    Product product;
    EditText name_ET;
    EditText carats_ET;
    EditText price_ET;
    Button takePhotoBtn;
    Boolean takePhotoBtn_isClicked;

    Bitmap imageBitmap;
    Button updateBtn;
    ProductViewModel viewModel;
    View view;
    ImageView imageView;

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
        updateBtn = view.findViewById(R.id.updateBtn_editProductFragment);
        takePhotoBtn = view.findViewById(R.id.btn_takePhoto_editProductFragment);
        takePhotoBtn_isClicked = false;
        imageView = view.findViewById(R.id.image_ImageView_EditeProductFragment);

        //getting all data from last fragment, and define the product.
        product = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProduct();

        //connect between the local parameters and getArguments() from last screen.
        name_ET.setText(product.getName__Product());
        carats_ET.setText(product.getCarat__Product());
        price_ET.setText(product.getPrice__Product());
        if (product.imageUrl__Product != null && product.imageUrl__Product != "") {
            Picasso.get().load(product.imageUrl__Product).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.diamond_ring);
        }

        //take a photo from camera
        takePhotoBtn.setOnClickListener(v -> {
            Log.d("TAG", "Take photo clicked");
            takePhotoBtn_isClicked = true;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                Log.d("TAG", "Start activity - take picture intent");
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

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

        if (!product.getName__Product().equals(name) || !product.getCarat__Product().equals(carat) ||
                !product.getPrice__Product().equals(price) || takePhotoBtn_isClicked) {

            product.setName__Product(name);
            product.setCarat__Product(carat);
            product.setPrice__Product(price);

            java.util.Date d = new Date();
            if (imageBitmap != null) {
                StoreModel.uploadImage(imageBitmap, "my_photo_" + d.getTime(), new StoreModel.Listener() {
                    @Override
                    public void onSuccess(String url) {
                        Log.d("TAG", "url: " + url);
                        product.setImageUrl__Product(url);
                    }

                    @Override
                    public void onFail() {
                    }
                });
            }


            ProductModel.instance.updateProduct(product, new ProductModel.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    NavController navCtrl = Navigation.findNavController(view);
                    NavDirections direction = ProductsListFragmentDirections.actionGlobalProductsListFragment();
                    navCtrl.navigate(direction);
                }
            });

        }


//        viewModel.update(product, new ProductModel.Listener() {
//            @Override
//            public void onComplete(Object data) {
//                Log.d("TAG", "update product success");
//                NavController navController = Navigation.findNavController(view);
//                // Back to list
//                navController.navigateUp();
//            }
//        });

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


}