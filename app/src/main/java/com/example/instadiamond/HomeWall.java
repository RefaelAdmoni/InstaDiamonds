package com.example.instadiamond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.zip.Inflater;

public class HomeWall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_wall);
    }


    class MyAdapter extends BaseAdapter{

        // The inflater take the xml file to view on the application.
        LayoutInflater inflater = (LayoutInflater) HomeWall.this.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // amount rows..

            return 0;
        }

        @Override
        public Object getItem(int position) {
            //
            return null;
        }

        @Override
        public long getItemId(int position) {
            //
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //back the view about number row.

            if(convertView == null){
                convertView = Inflater.inflate(R.layout.list_row, null);
            }
            return convertView;
        }
    }

}