package com.lanchonete.funcionario.get.doce.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;

public class DoceHolder extends RecyclerView.ViewHolder {

    final TextView nome_doce;
    final TextView descricao_doce;
    final TextView valor_doce;

    public DoceHolder(@NonNull View itemView) {
        super(itemView);

        //N vai ser necessário as imagens nesse recycler view
        nome_doce = itemView.findViewById(R.id.docesListItem_nome);
        descricao_doce = itemView.findViewById(R.id.docesListItem_descricao);
        valor_doce = itemView.findViewById(R.id.docesListItem_valor);
    }

}