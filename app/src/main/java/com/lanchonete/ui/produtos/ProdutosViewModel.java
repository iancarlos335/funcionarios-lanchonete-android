package com.lanchonete.ui.produtos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProdutosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProdutosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esse é o fragmento produtos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}