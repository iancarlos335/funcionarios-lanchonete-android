package com.lanchonete.ui.salgados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalgadosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SalgadosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Salgados fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}