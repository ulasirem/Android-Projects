package com.iremulas.simpsonbook.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.iremulas.simpsonbook.Model.Simpson;
import com.iremulas.simpsonbook.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imageView = findViewById(R.id.imageView);
        TextView nameText = findViewById(R.id.nameText);
        TextView jobText = findViewById(R.id.jobText);

        Intent intent = getIntent();
        Simpson selectedSimpson = (Simpson)intent.getSerializableExtra("selectedSimpson"); //MainActivty de tanımladığımız Intent in burada da göstermemiz gerek

        //SimpsonList e eklediğiiz tüm verileri sırayala ekranda görebilirim
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),selectedSimpson.getPictureInteger());
        imageView.setImageBitmap(bitmap);
        nameText.setText(selectedSimpson.getName());
        jobText.setText(selectedSimpson.getJob());

    }
}