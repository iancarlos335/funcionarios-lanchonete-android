package com.lanchonete.funcionario.get.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.model.Bebida;

import java.util.List;

public class BebidaAdapter extends RecyclerView.Adapter<BebidaHolder>{

    private final List<Bebida> bebidaList;

    public BebidaAdapter(List<Bebida> bebidaList) {
        this.bebidaList = bebidaList;
    }

    @NonNull
    @Override
    public BebidaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bebidas_items, parent, false); //esse macete do binding.getRoot() talvez me salve
        return new BebidaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BebidaHolder holder, int position) { //É esse position que armazena cada item pelo id
        Bebida bebida = bebidaList.get(position);
        String strValue = Double.toString(bebida.getValor());

        //String strImage = String.valueOf(new BebidaHolder.ImageDownloader().execute(bebida.getImagem())); //vo chamar a string completa do banco

        holder.nome_bebida.setText(bebida.getNomeBebida());
        holder.descricao_bebida.setText(bebida.getDescricao());
        holder.valor_bebida.setText(strValue);
        //holder.imagem_bebida.setContentDescription(bebida.getImagem()); //talvez ele adicione tudo de acordo com a posição da imagem
         //coloquei isso pq eu acho q se eu n colocar ele n vai rodar novas imagens nesse ImageView
         // pensei aqui q talvez nem precise configurar isso, pq a img não vai estar no app de qualquer forma, isso é só pra nortear novas atualizações no banco


    }

    @Override
    public int getItemCount() { //vai adicionar novos items, dependendo de quantos itens estiverem no nosso contrutor
        return bebidaList.size();
    }



}
