package com.example.instadiamond;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instadiamond.model.ProductFirebase;
import com.example.instadiamond.model.ProductModel;
import com.example.instadiamond.model.Product;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;


public class ProductsListFragment extends Fragment {
    RecyclerView prd_List;
    List<Product> data = new LinkedList<Product>();
    ProductListAdapter adapter;
    ProductListViewModel viewModel;
    LiveData<List<Product>> liveData;


    interface Delegate{
        void onItemSelected(Product Product);
    }

    Delegate parent;

    public ProductsListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Delegate){
            parent = (Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement Delegate");
        }
        setHasOptionsMenu(true);

        viewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        prd_List = view.findViewById(R.id.products_list_list);
        prd_List.setHasFixedSize(true);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        prd_List.setLayoutManager(layoutManager);

         adapter = new ProductListAdapter();
         prd_List.setAdapter(adapter);

        adapter.setOnItemClickListener((position) -> {
                Log.d("Tag","row " + position + " was clicked");
                Product product = data.get(position);
                parent.onItemSelected(product);
        });

        liveData = viewModel.getData();
        if (liveData == null) {

        }
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                data = products;
                adapter.notifyDataSetChanged();
            }
        });

        SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.product_list_swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh(new ProductModel.CompListener() {
                    @Override
                    public void onComplete() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
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
        TextView imageUrl;
        CheckBox cb;
        ImageView image;
        Product product;

        public ProductRowViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_TV_diamond_Name);
            carat = itemView.findViewById(R.id.row_TV_diamondCarats);
            price = itemView.findViewById(R.id.row_TV_diamondPrice);
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
            carat.setText(prd.carat__Product);
            price.setText(prd.price__Product);
            Log.d("TAG","ImageUrl = " + prd.imageUrl__Product);
            if (prd.imageUrl__Product != null && prd.imageUrl__Product != ""){
                Picasso.get().load(prd.imageUrl__Product).into(image);
            } else {
                image.setImageResource(R.drawable.diamond_ring);
            }
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
            Product prd = data.get(position);
            holder.bind(prd);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.products_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_product_list_add:
                Log.d("TAG","fragment handle add menu");
                //MyDatePickerFragment picker = new MyDatePickerFragment();           //The picker created his fragment
                //picker.show(getParentFragmentManager(),"TAG");
                onClickAddProduct();

                return true;
            case R.id.menu_product_list_info:
                Log.d("TAG","fragment handle info menu");
                AlertDialogFragment dialog = AlertDialogFragment.newInstance("Product App Info", "Welcome to the Diamonds app info page...");
                dialog.show(getParentFragmentManager(),"TAG");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickAddProduct() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.home_nav_host);
        NavDirections direction = AddNewProductFragmentDirections.actionGlobalAddNewProductFragment();
        navController.navigate(direction);
    }
}