package com.lanchonete.funcionario.get.doce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.model.Doce;

import java.util.List;

public class DoceAdapter extends RecyclerView.Adapter<DoceHolder>{

    private final List<Doce> doceList;

    public DoceAdapter(List<Doce> doceList) {
        this.doceList = doceList;
    }

    @NonNull
    @Override
    public DoceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_doces_itens, parent, false); //esse macete do binding.getRoot() talvez me salve
        return new DoceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoceHolder holder, int position) { //É esse position que armazena cada item pelo id
        Doce doce = doceList.get(position);
        String strValue = Double.toString(doce.getValor());
        //String strImage = String.valueOf(new BebidaHolder.ImageDownloader().execute(bebida.getImagem())); //vo chamar a string completa do banco

        holder.nome_doce.setText(doce.getNomeDoce());
        holder.descricao_doce.setText(doce.getDescricao());
        holder.valor_doce.setText(strValue);
        //holder.imagem_bebida.setContentDescription(bebida.getImagem()); //talvez ele adicione tudo de acordo com a posição da imagem
        //coloquei isso pq eu acho q se eu n colocar ele n vai rodar novas imagens nesse ImageView
        // pensei aqui q talvez nem precise configurar isso, pq a img não vai estar no app de qualquer forma, isso é só pra nortear novas atualizações no banco


    }

    @Override
    public int getItemCount() { //vai adicionar novos items, dependendo de quantos itens estiverem no nosso contrutor
        return doceList.size();
    }



}
