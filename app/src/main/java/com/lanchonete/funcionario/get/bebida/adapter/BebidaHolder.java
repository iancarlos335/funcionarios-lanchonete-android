package com.lanchonete.funcionario.get.bebida.adapter;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;

public class BebidaHolder extends RecyclerView.ViewHolder {

    final TextView nome_bebida;
    final TextView descricao_bebida;
    final TextView valor_bebida;
    final Button delete_item;

    private BebidaAdapter bebidaAdapter;

    public BebidaHolder(@NonNull View itemView) {
        super(itemView);

        //N vai ser necessário as imagens nesse recycler view
        nome_bebida = itemView.findViewById(R.id.bebidasListItem_nome);
        descricao_bebida = itemView.findViewById(R.id.bebidasListItem_descricao);
        valor_bebida = itemView.findViewById(R.id.bebidasListItem_valor);
        delete_item = itemView.findViewById(R.id.btnDelete);
    }

}