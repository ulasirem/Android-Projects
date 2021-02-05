package com.iremulas.artbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<Integer> idArray;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        nameArray = new ArrayList<String>();
        idArray = new ArrayList<Integer>();

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,nameArray);
        listView.setAdapter(arrayAdapter);

        //listedeki bir elemana tıkladığımızda neler olacak
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);  //mainactivty den mainactivity2 ye gidecek
                intent.putExtra("artId",idArray.get(i));    //burada idArray in elmeanlarına ulaşacak
                intent.putExtra("info","old");  //önceden kaydedilenlere ulaşmak için pıtExtra ekledik
                startActivity(intent);
            }
        });

        getData();

    }


    public void getData(){  //eklenen verilerin liste halinde gösterilmesi

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            //bu imleç ile databse de sorgu yapabiliriz
            Cursor cursor = database.rawQuery("SELECT * FROM arts",null);
            int nameIx = cursor.getColumnIndex("artname");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()){
                nameArray.add(cursor.getString(nameIx));
                idArray.add(cursor.getInt(idIx));
            }
            arrayAdapter.notifyDataSetChanged();    //eklenen verileri de göster

            cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflater
        MenuInflater menuInflater = getMenuInflater();  //oluşturdugumuz menüyü aktivitemize bağladık
        menuInflater.inflate(R.menu.add_art,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //bu menuden herhangi bir şey seçilirse ne yapmalyız

        if(item.getItemId() == R.id.add_art_item){
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            //menuden yeni bir eleman eklendiğini belirmek için
            intent.putExtra("info","new");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}