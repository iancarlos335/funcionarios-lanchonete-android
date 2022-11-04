package com.lanchonete.ui.doces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DocesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Doces fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}