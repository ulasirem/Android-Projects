package com.iremulas.simpsonbook.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iremulas.simpsonbook.Model.Simpson;
import com.iremulas.simpsonbook.R;

import java.util.ArrayList;

//ArrayAdapter yerine kendi Adapterımı oluşturdum
public class CustomAdapter extends ArrayAdapter<Simpson> {
    private ArrayList<Simpson> simpsons;
    private Activity context;


    public CustomAdapter(ArrayList<Simpson> simpsons, Activity context){
        super(context, R.layout.custom_view,simpsons);   //context resource ve hangi liste ile bağlıyacağım
        this.simpsons = simpsons;
        this.context = context;
    }


    //bu methodu custom_view ile CustomAdapter ı birbirine bağlamak için yazdık
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true); //custom_view deki görünümü kullanmamızı sağlayacak
        TextView nameView = customView.findViewById(R.id.customTextView);   //custom_view deki customTextView olarak tanımlanan yere isim geleceğini söyledik
        nameView.setText(simpsons.get(position).getName()); //artık listView de sadece isim görünecek
        return customView;
    }
}
