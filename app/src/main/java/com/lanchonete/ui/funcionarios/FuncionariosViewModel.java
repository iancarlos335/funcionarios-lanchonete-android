package com.lanchonete.ui.funcionarios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FuncionariosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FuncionariosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esse é o fragmento Funcionarios");
    }

    public LiveData<String> getText() {
        return mText;
    }
}