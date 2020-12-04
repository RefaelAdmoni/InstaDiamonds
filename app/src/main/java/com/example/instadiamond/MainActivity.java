package com.example.instadiamond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            //control the login button.
            @Override
            public void onClick(View view) {
                //check the login parameters and enter to profile.
                Intent intent = new Intent(MainActivity.this, Wall.class);
                startActivity(intent);
                finish();
            }
        });





    }
}