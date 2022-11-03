package com.lanchonete.ui.pedidos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PedidosViewModel extends ViewModel {

    private final MutableLiveData<String> mText; //O interessante disso, é que ele vai processando as informações de acordo com a a chegada de novos dados, e sua necessidade de orientação

    public PedidosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esse é o fragmento pedidos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}