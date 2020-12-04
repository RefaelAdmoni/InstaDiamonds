package com.example.instadiamond;

interface MyButtonListener{
    void onClick();
}

public class MyButton {
    private MyButtonListener listener;

    void setOnClickListener(MyButtonListener listener){
        this.listener = listener;
    }
    void click(){
        //Here we capture the user on this button
        listener.onClick();
    }

//take data back..//
    interface Callback{
        void onComlete(/*parameters back*/);
    }
    void getData(Callback callback){
        //.....
    }
}

