package com.lanchonete.ui.bebidas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BebidasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BebidasViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is Bebidas fragment");



    }

    public LiveData<String> getText() {
        return mText;
    }
}