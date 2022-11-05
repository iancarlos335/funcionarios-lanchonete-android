package com.lanchonete.funcionario.get.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.model.Bebida;

import java.util.List;

public class BebidaAdapter extends RecyclerView.Adapter<BebidaHolder> {

    private List<Bebida> bebidaList;

    public BebidaAdapter(List<Bebida> bebidaList) {
        this.bebidaList = bebidaList;
    }

    @NonNull
    @Override
    public BebidaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BebidaHolder holder, int position) {

    }

    @Override
    public int getItemCount() { //vai adicionar novos items, dependendo de quantos itens estiverem no nosso contrutor
        return bebidaList.size();
    }
}
