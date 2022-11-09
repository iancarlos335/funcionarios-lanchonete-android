package com.lanchonete.funcionario.get.salgado.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;

public class SalgadoHolder extends RecyclerView.ViewHolder {

    final TextView nome_salgado;
    final TextView descricao_salgado;
    final TextView valor_salgado;

    public SalgadoHolder(@NonNull View itemView) {
        super(itemView);

        //N vai ser necessário as imagens nesse recycler view
        nome_salgado = itemView.findViewById(R.id.salgadosListItem_nome);
        descricao_salgado = itemView.findViewById(R.id.salgadosListItem_descricao);
        valor_salgado = itemView.findViewById(R.id.salgadosListItem_valor);
    }

}