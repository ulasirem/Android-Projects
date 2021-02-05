package com.iremulas.artbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {

    Bitmap Image;
    ImageView selectImage;
    EditText artName, painterNameText, yearText;
    Button button;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        selectImage = findViewById(R.id.selectImage);
        artName = findViewById(R.id.artName);
        painterNameText = findViewById(R.id.painterNameText);
        yearText = findViewById(R.id.yearText);
        button = findViewById(R.id.button);

        database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);

        //MainActivty den yollanılan inten i almak için
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if(info.matches("new")){    //adsArt a tıklandıysa yeni bir oge ekle
            //yeni bir oge eklemek istersek o anda dolu olan her yeri boşalt
            artName.setText("");
            painterNameText.setText("");
            yearText.setText("");
            button.setVisibility(View.VISIBLE);

            Bitmap Image = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.image);
            selectImage.setImageBitmap(Image);

        }
        else{  //old ise
            //yanlış bir işlem olur ise ilk ogeyi goster
            int artId = intent.getIntExtra("artId",1);
            button.setVisibility(View.INVISIBLE);   //eski oge varken butonun gösterilmesine gerek yok

            //hangi satıra tıklandıysa onu içindeki elemanları göster
            try {
                //id sütununa göre bir arama gerçekleştiriceğiz id integerdan stringe çevrilir
                Cursor cursor = database.rawQuery("SELECT * FROM arts WHERE id = ?",new String[]{String.valueOf(artId)});

                //tüm sutunları gösterebilmek için tanımlıyoruz
                int artNameIx = cursor.getColumnIndex("artname");
                int painterNameIx = cursor.getColumnIndex("paintername");
                int yearIx = cursor.getColumnIndex("year");
                int imageIx = cursor.getColumnIndex("image");

                while (cursor.moveToNext()){
                    artName.setText(cursor.getString(artNameIx));
                    painterNameText.setText(cursor.getString(painterNameIx));
                    yearText.setText(cursor.getString(yearIx));

                    //görseli ekranda gösterirken byte çevirlimiş halini eski haline getirmemiz gerekir
                    byte[] bytes = cursor.getBlob(imageIx);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    selectImage.setImageBitmap(bitmap);
                }



            }
            catch (Exception e){

            }
        }

    }

    public void selectImage(View view){
        //Kullanıcıdan izin alma
        //eğer henüz izin verilmedi ise(not Grandet) - izinleri(requestPermissions) almak için
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        //Eğer daha önceden izin almışsak direk galeriye gitmek için
        else{
            Intent intenToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);   //eğer izin var ise galeriye götürmemiz gerekir
            startActivityForResult(intenToGallery,2);   //bir sonuç geleceği(URI) için forResult dedik
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //izin verildi ise
        if(requestCode == 1){
            //izin verildiğinde içinde eleman var ise
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //kullanıcıyı galeriye götür
                Intent intenToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);   //eğer izin var ise galeriye götürmemiz gerekir
                startActivityForResult(intenToGallery,2);   //bir sonuç geleceği(URI) için forRsult dedik
            }
        }
    }
    //Görsel seçmek için
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==2 && resultCode ==RESULT_OK && data!=null){

            Uri imageData = data.getData();

            try {
                //versiyon 28 ve 28 den büyük ise decoder kullan
                if (Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    Image = ImageDecoder.decodeBitmap(source);
                    selectImage.setImageBitmap(Image);
                }
                else {
                    Image = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    selectImage.setImageBitmap(Image);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void save(View view){

        //sqlite a kaydedeceğimiz verileri alırız
        String artNameText = artName.getText().toString();
        String painterName = painterNameText.getText().toString();
        String year = yearText.getText().toString();

        Bitmap smallerImage = makeSmallerImage(Image,300);  //method ile görseli küçültme
        //görseli veriye çevirme işlemi
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallerImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray = outputStream.toByteArray();


        //alınan verileri SQLite a kaydetme
        try {
            database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS arts(id INTEGER PRIMARY KEY, artname VARCHAR, paintername VARCHAR, year VARCHAR, image BLOB )");
            String sqlString = "INSERT INTO arts(artname, paintername, year, image) VALUES (?, ?, ?, ?)";//dışardan veri almak için ? kullan
            //bir stringi sql komutu yapma
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            //indexler ile verileri bağlama
            sqLiteStatement.bindString(1,artNameText);
            sqLiteStatement.bindString(2,painterName);
            sqLiteStatement.bindString(3,year);
            sqLiteStatement.bindBlob(4,byteArray);  //görsel için Blob
            sqLiteStatement.execute();

        }
        catch (Exception e){
        }
        
        //açık activty lerin kapatılmasını sağlar
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //görseli küçültmek için yazılmış bir method
    public Bitmap makeSmallerImage(Bitmap image,int maximumSize){
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width / (float)height;

        if (bitmapRatio > 1){
            width = maximumSize;
            height = (int)(width / bitmapRatio);
        }
        else {
            height = maximumSize;
            width =(int)(height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,width,height,true);
    }
}