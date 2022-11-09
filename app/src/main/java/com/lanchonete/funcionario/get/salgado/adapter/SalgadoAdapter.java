package com.lanchonete.funcionario.get.salgado.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.model.Salgado;


import java.util.List;

public class SalgadoAdapter extends RecyclerView.Adapter<SalgadoHolder>{

    private final List<Salgado> salgadoList;

    public SalgadoAdapter(List<Salgado> salgadoList) {
        this.salgadoList = salgadoList;
    }

    @NonNull
    @Override
    public SalgadoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_salgados_itens, parent, false); //esse macete do binding.getRoot() talvez me salve
        return new SalgadoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalgadoHolder holder, int position) { //É esse position que armazena cada item pelo id
        Salgado salgado = salgadoList.get(position);
        String strValue = Double.toString(salgado.getValor());
        //String strImage = String.valueOf(new BebidaHolder.ImageDownloader().execute(bebida.getImagem())); //vo chamar a string completa do banco

        holder.nome_salgado.setText(salgado.getNomeSalgado());
        holder.descricao_salgado.setText(salgado.getDescricao());
        holder.valor_salgado.setText(strValue);
        //holder.imagem_bebida.setContentDescription(bebida.getImagem()); //talvez ele adicione tudo de acordo com a posição da imagem
        //coloquei isso pq eu acho q se eu n colocar ele n vai rodar novas imagens nesse ImageView
        // pensei aqui q talvez nem precise configurar isso, pq a img não vai estar no app de qualquer forma, isso é só pra nortear novas atualizações no banco


    }

    @Override
    public int getItemCount() { //vai adicionar novos items, dependendo de quantos itens estiverem no nosso contrutor
        return salgadoList.size();
    }



}