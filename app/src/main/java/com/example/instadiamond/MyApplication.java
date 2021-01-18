package com.example.instadiamond;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context context;                      //context help to R/W from file or DB..

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
