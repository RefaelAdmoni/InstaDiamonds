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
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadiamond.HomeActivity;
import com.example.instadiamond.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity {
    TextView _ToRegisterBtn;
    EditText email_et, password_et;
    Button loginBtn;
    FirebaseAuth firebaseAuth;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       email_et          = findViewById(R.id.Email_login_activity);
       password_et       = findViewById(R.id.Password_login_activity);
       _ToRegisterBtn    = findViewById(R.id.registered_tv_LoginActivity);
       loginBtn          = findViewById(R.id.btn_Login_activity);

       _ToRegisterBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),RegisterUser.class));
           }
       });

       firebaseAuth = FirebaseAuth.getInstance();

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email = email_et.getText().toString().trim();
               String password = password_et.getText().toString().trim();

               if (TextUtils.isEmpty(email)) {
                   email_et.setError("Email is required.");
                   return;
               }

               if (TextUtils.isEmpty(password)) {
                   password_et.setError("Password is required.");
                   return;
               }

               if (password.length() < 6) {
                   password_et.setError("Password must be up to 6 characters.");
                   return;
               }

               // auth the user

               firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           Log.d("TAG", "user is login successful");
                           Toast.makeText(LoginUser.this, "User logged in" , Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                       } else {
                           Log.d("TAG", "user login failed");
                           Toast.makeText(LoginUser.this, "Error - User login failed! " +task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                       }
                   }
               });

           }
       });





   }
}