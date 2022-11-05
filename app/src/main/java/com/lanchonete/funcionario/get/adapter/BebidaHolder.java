package com.lanchonete.funcionario.get.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;

public class BebidaHolder extends RecyclerView.ViewHolder {

    TextView nome_bebida, descricao_bebida, valor_bebida;
    ImageView imagem_bebida;

    public BebidaHolder(@NonNull View itemView) {
        super(itemView);
        nome_bebida = itemView.findViewById(R.id.bebidasListItem_nome);
        descricao_bebida = itemView.findViewById(R.id.bebidasListItem_descricao);
        valor_bebida = itemView.findViewById(R.id.bebidasListItem_valor);
        imagem_bebida = itemView.findViewById(R.id.bebidasListItem_imagem);
    }
}
