package com.lanchonete.ui.bebidas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BebidasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BebidasViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is Bebidas fragment");
        // vo criar as mesmas coisas, a unica diferença, é que eu vou ter que puxar isso da lista de bebidas,, pois os campos não serão preenchidos com campos estáticos

    }

    public LiveData<String> getText() {
        return mText;
    }
}