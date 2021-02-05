package com.iremulas.signalbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;



public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.imageView);
        TextView signalNameText = findViewById(R.id.signalNameText);
        TextView countryNameText = findViewById(R.id.countryNameText);

        Intent intent = getIntent();
        String signalNames = intent.getStringExtra("name");
        signalNameText.setText(signalNames);

        String countryNames = intent.getStringExtra("country");
        countryNameText.setText(countryNames);

        Singleton singleton = Singleton.getInstance();
        imageView.setImageBitmap(singleton.getChoseImage());



    }
}