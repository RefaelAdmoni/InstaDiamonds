package com.example.instadiamond;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.instadiamond.Authentication.MainActivity;
import com.google.firebase.auth.FirebaseAuth;


public class HomeFragment extends Fragment {

    TextView name_tv;
    Button logout_btn;
    FirebaseAuth firebaseAuth;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment

        firebaseAuth = FirebaseAuth.getInstance();

        name_tv = view.findViewById(R.id.name_tv_home_frag);
        logout_btn = view.findViewById(R.id.logout_btn_home);

        if (firebaseAuth.getCurrentUser() != null) {
            name_tv.setText(firebaseAuth.getCurrentUser().getEmail());
        }

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
            }
        });

        return view;
    }
}