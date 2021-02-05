package com.iremulas.alertdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,"Toast Message",Toast.LENGTH_LONG).show(); //uygulmanın en altında bir kaç saniye gösterilen mesaj
                                                                                    //this yerine MainActivity.this de yazabilirim
    }

    public void save(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Save"); //mesajımın başlığı
        alert.setMessage("Are you sure ?"); //mesajım
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();   //getAppicationContext ile tüm uygulmada b tepkiyi vermesini sağlarız
                                                                                                //getApplicationContext yerine MainActivity.this de yazabiliriz
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"Not Saved",Toast.LENGTH_LONG).show();
            }
        });
        alert.show();
    }
}