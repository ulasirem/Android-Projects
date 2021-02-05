package com.iremulas.tebbedfragment.ui.main;

import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.iremulas.tebbedfragment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    PageViewModel pageViewModel;

    //newInstance.FirsFragment yazıldığında yeni bir FirstFragment oluştur
    public static FirstFragment newInstance(){
        return new FirstFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //context için this ya da getAplicationContext kullanamam
        Toast.makeText(getActivity().getApplicationContext(),"Toast Message",Toast.LENGTH_LONG).show();

        //PageViewModel verileri güvenli ve canlı olarak saklamamızı sağlar
        //Fragment ın bağlı olduğu activity i alıyoruz PageViewModel sınıfında çağırıyorum
        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //fragment_first.xml ile FirstFragment ı biirbirine bağladık
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    //görünüm oluşturulduktan sonra yapılacak işlemler
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editText = view.findViewById(R.id.editText);
        //fragmanlar arası iletişim
        //editTExt imizde bir değişiklik olup olmadığını gözlemler
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //text de değişiklik olduğu anda PageViewModele kayıt et
                pageViewModel.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}