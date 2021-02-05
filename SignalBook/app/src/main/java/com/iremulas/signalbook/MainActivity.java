package com.iremulas.signalbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        //Data

        final ArrayList<String> signalNames = new ArrayList<>();
        signalNames.add("Cheops Pyramid");
        signalNames.add("The Temple of Artemis");
        signalNames.add("The hanging gardens of Babylon");
        signalNames.add("statue of Zeus");

        final ArrayList<String> countryNames = new ArrayList<>();
        countryNames.add("Egypt");
        countryNames.add("Türkiye");
        countryNames.add("Irak");
        countryNames.add("Greece");

        Bitmap keops = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.keops);
        Bitmap artemis = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.artemis);
        Bitmap babil = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.babil);
        Bitmap zeus = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.zeus);

        final ArrayList<Bitmap> signalImages = new ArrayList<>();
        signalImages.add(keops);
        signalImages.add(artemis);
        signalImages.add(babil);
        signalImages.add(zeus);

        //ListView

        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_expandable_list_item_1,signalNames);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              //  System.out.println(signalNames.get(i)); amacımız log da kontrol etmek

                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("name",signalNames.get(i));

                intent.putExtra("country",countryNames.get(i));

                Singleton singleton = Singleton.getInstance();
                singleton.setChoseImage(signalImages.get(i));

                startActivity(intent);
            }
        });
    }
}