package com.example.instadiamond.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProductFirebase {
    final static String PRODUCT_COLLECTION = "products";

    public static void getAllProductsSince(long since, final ProductModel.Listener<List<Product>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since,0);
        db.collection(PRODUCT_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Product> stData = null;
                if (task.isSuccessful()){
                    stData = new LinkedList<Product>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Map<String,Object> json = doc.getData();
                        Product product = factory(json);
                        stData.add(product);
                    }
                }
                listener.onComplete(stData);
                Log.d("TAG","refresh " + stData.size());
            }
        });
    }

    public static void getAllProducts(final ProductModel.Listener<List<Product>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PRODUCT_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Product> prdData = null;
                if (task.isSuccessful()){
                    prdData = new LinkedList<Product>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Product product = doc.toObject(Product.class);
                        prdData.add(product);
                    }
                }
                listener.onComplete(prdData);
            }
        });
    }

//    public static void addProduct (Product product,final ProductModel.Listener<Boolean> listener) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection(PRODUCT_COLLECTION).document(product.getId__Product()).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (listener!=null){
//                    listener.onComplete(task.isSuccessful());
//                }
//            }
//        });
//    }

    // Auto generate id
    public static void addProduct (Product product,final ProductModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(PRODUCT_COLLECTION).document();
        product.setId__Product(documentReference.getId());
        documentReference.set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    // update
    public static void updateProduct (Product product,final ProductModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(PRODUCT_COLLECTION).document(product.id__Product);
        documentReference.set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    private static Product factory(Map<String, Object> json){
        Product product = new Product();
        product.id__Product = (String)json.get("id__Product");
        product.name__Product = (String)json.get("name__Product");
        product.carat__Product = (String)json.get("carat__Product");
        product.price__Product = (String)json.get("price__Product");
        product.imageUrl__Product = (String)json.get("imageUrl__Product");
        product.isChecked__Product = (boolean)json.get("isChecked__Product");
        //Timestamp ts = (Timestamp)json.get("lastUpdated");
        //if (ts != null) product.lastUpdated = ts.getSeconds();
        return product;
    }

    private static Map<String, Object> toJson(Product product){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id__Product", product.id__Product);
        result.put("name__Product", product.name__Product);
        result.put("carat__Product", product.carat__Product);
        result.put("price__Product", product.price__Product);
        result.put("imageUrl__Product", product.imageUrl__Product);
        result.put("isChecked__Product", product.isChecked__Product);
        //result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

}





//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);

// Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });
//    }

