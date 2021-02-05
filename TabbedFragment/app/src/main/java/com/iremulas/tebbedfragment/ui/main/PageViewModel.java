package com.iremulas.tebbedfragment.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

//Değiştirebiliceğimiz bir data oluştururuz
public class PageViewModel extends ViewModel {

    private MutableLiveData<String> nameInput = new MutableLiveData<>();

    public void setName(String name) {
        nameInput.setValue(name);
    }

    public LiveData<String> getName() {
        return nameInput;
    }
}