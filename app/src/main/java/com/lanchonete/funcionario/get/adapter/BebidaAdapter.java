package com.lanchonete.funcionario.get.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.databinding.FragmentBebidasBinding;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bebidas_items, parent, true); //eu vou chamar ele la no fragment, que usa root, se eu n me engano
        return new BebidaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BebidaHolder holder, int position) {
        Bebida bebida = bebidaList.get(position);
        String strvalor = holder.valor_bebida.getTransformationMethod(bebida.getValor().toString());

        holder.nome_bebida.setText(bebida.getNomeBebida());
        holder.descricao_bebida.setText(bebida.getDescricao());

    }

    @Override
    public int getItemCount() { //vai adicionar novos items, dependendo de quantos itens estiverem no nosso contrutor
        return bebidaList.size();
    }
}
