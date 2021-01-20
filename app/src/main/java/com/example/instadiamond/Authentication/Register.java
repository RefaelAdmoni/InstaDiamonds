package com.example.instadiamond.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadiamond.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText _FullName,_Email,_Password;
    Button _RegisterBtn;
    TextView _ToLoginBtn;
    FirebaseAuth _FirebaseAuth;
    ProgressBar _progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        _FullName       = findViewById(R.id.Full_name_register_activity);
        _Email          = findViewById(R.id.Email_Register_activity);
        _Password       = findViewById(R.id.Password_Register_activity);
        _RegisterBtn     = findViewById(R.id.btn_register_activity);
        _ToLoginBtn       = findViewById(R.id.registered_tv_register_activity);
        _progressBar = findViewById(R.id.progressBar_registerActivity);

        _FirebaseAuth = FirebaseAuth.getInstance();

        //check if the user is already registered
        if (_FirebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        _ToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


        _RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _Email.getText().toString().trim();
                String password = _Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    _Email.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    _Password.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6){
                    _Password.setError("Password must be 6 characters or more.");
                    return;
                }

                _progressBar.setVisibility(View.VISIBLE);

                //Register the user to firebase

                _FirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "user is created successful");
                            Toast.makeText(Register.this, "User Created" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            Log.d("TAG", "user is create failed");
                            Toast.makeText(Register.this, "Error - User create failed! " +task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                            _progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });
    }


}









