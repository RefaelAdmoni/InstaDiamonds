package com.example.instadiamond;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instadiamond.model.Model;
import com.example.instadiamond.model.Product;

import java.util.List;


public class ProductsListFragment extends Fragment {

    List<Product> allDataProducts;
    RecyclerView prd_List;


    interface Delgate{
        void onItemSelected(Product Product);
    }
    Delgate parent;


    public ProductsListFragment() {
        allDataProducts = Model.instance.getAllProducts();                    //getting all the products.
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Delgate){
            parent = (Delgate) getActivity();
        } else throw new RuntimeException(context.toString()
                + "must implement Delegate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        prd_List = view.findViewById(R.id.products_list_list);
        prd_List.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        prd_List.setLayoutManager(layoutManager);

         ProductListAdapter adapter = new ProductListAdapter();
         prd_List.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("Tag","row " + position + " was clicked");
                Product product = allDataProducts.get(position);
                parent.onItemSelected(product);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    static class ProductRowViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView carat;
        TextView price;
        CheckBox cb;
        ImageView image;
        Product product;

        public ProductRowViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_titleName);
            carat = itemView.findViewById(R.id.row_TV_diamondCarats);
            price = itemView.findViewById(R.id.row_DaimondPrice_TV);
            cb = itemView.findViewById(R.id.row_checkBox);
            image = itemView.findViewById(R.id.row_image_TV);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.isChecked__Product = cb.isChecked();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onClick(position);
                        }
                    }
                }
            });
        }
        public void bind(Product prd){
            name.setText(prd.name__Product);
            carat.setText(prd.Carat_Diamond__Product+"");
            price.setText(prd.price__Product+"");
            cb.setChecked(prd.isChecked__Product);
            product = prd;
        }
    }

    interface OnItemClickListener{          //listener to row click
        void onClick(int position);
    }

    class ProductListAdapter extends RecyclerView.Adapter<ProductRowViewHolder>{
        private OnItemClickListener listener;
        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }


        @NonNull
        @Override
        public ProductRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //create new view object row
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_row, parent,
                    false);

            //bind the view in View Holder
            ProductRowViewHolder vh = new ProductRowViewHolder(v, listener);
            return vh;
        }


        @Override
        public void onBindViewHolder(@NonNull ProductRowViewHolder holder, int position) {
            //bind data and row according to position
            Product prd = allDataProducts.get(position);
            holder.bind(prd);
        }

        @Override
        public int getItemCount() {
            return allDataProducts.size();
        }
    }
}