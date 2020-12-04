package com.example.instadiamond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Wall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        ListView list = findViewById(R.id.activity_wall_listView);

        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        //catch the click user on the list row:
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG","row click: " + position);
            }
        });

    }


    class MyAdapter extends BaseAdapter{

        // The inflater take the xml file to view on the application.
        LayoutInflater inflater = (LayoutInflater) Wall.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // amount rows..

            return 11;
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
            //back the view per number row.

            if(convertView == null){
                convertView = inflater.inflate(R.layout.list_row, null);
            }

            //init data row
            TextView diamond_Name_TV = convertView.findViewById(R.id.row_TV_diamond_Name);
            TextView daimond_Karats_TV = convertView.findViewById(R.id.row_TV_diamondKarat);
            TextView daimond_Price_TV = convertView.findViewById(R.id.row_TV_diamondPice);
            ImageView image_TV = convertView.findViewById(R.id.row_image_TV);

            diamond_Name_TV.setText("Rafi AAAAAA");
            daimond_Karats_TV.setText("$$$ ++  " + position);
            daimond_Price_TV.setText(position + "$");


            return convertView;
        }
    }

}