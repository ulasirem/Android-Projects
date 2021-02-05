package com.iremulas.simpsonbook.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iremulas.simpsonbook.Adapter.CustomAdapter;
import com.iremulas.simpsonbook.Model.Simpson;
import com.iremulas.simpsonbook.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        Simpson homer = new Simpson("Homer Simpson","Nuclear",R.drawable.homer);
        Simpson bart = new Simpson("Bart Simpson","Student",R.drawable.bart);
        Simpson lisa = new Simpson("Lisa Simpson","Student",R.drawable.lisa);

        //Simpson clasımdan bir arraylist oluşturup tüm bilgileri içine atacağım
        final ArrayList<Simpson> simpsonList = new ArrayList<>();
        simpsonList.add(homer);
        simpsonList.add(bart);
        simpsonList.add(lisa);

        CustomAdapter customAdapter = new CustomAdapter(simpsonList,MainActivity.this);
        listView.setAdapter(customAdapter); //artık her şeyi birbirine bağladık (ListView ile ArrayList i)

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("selectedSimpson", simpsonList.get(i)); //simpsonList de bulanan verileri seri halinde gönderbilmek için serileştirmek gerek
                startActivity(intent);
            }
        });

    }
}