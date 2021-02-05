package com.iremulas.tebbedfragment.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iremulas.tebbedfragment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    PageViewModel pageViewModel;

    //newInstance.SecondFragment yazıldığında yeni bir SecondFragment oluştur
    public static SecondFragment newInstance() {
        return  new SecondFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PageViewModel verileri güvenli ve canlı olarak saklamamızı sağlar
        //Fragment ın bağlı olduğu activity i alıyoruz PageViewModel sınıfında çağırıyorum
        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView textView = view.findViewById(R.id.textView);

        //FirstFragment dan gönderilen name i PageView den alabilmek için
        //observe pageViewModel da bir değişiklik var ise hemen yansıtır
        pageViewModel.getName().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s); //böylece değiştirilen String i texView ıma koyabiliyorum
            }
        });
    }
}