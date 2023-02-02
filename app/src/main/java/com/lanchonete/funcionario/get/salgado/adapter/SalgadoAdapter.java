package com.lanchonete.funcionario.get.salgado.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.model.Doce;
import com.lanchonete.model.Salgado;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.DoceAPI;
import com.lanchonete.retrofit.api.SalgadoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.LinkedList;
import java.util.List;

public class SalgadoAdapter extends RecyclerView.Adapter<SalgadoAdapter.SalgadoHolder> {

    private final LinkedList<Salgado> salgadoList;

    public SalgadoAdapter(LinkedList<Salgado> salgadoList) {
        this.salgadoList = salgadoList;
    }

    @NonNull
    @Override
    public SalgadoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_salgados_itens, parent, false);
        return new SalgadoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalgadoHolder holder, int position) {
        Salgado salgado = salgadoList.get(position);
        String strValue = Double.toString(salgado.getValor());

        holder.nome.setText(salgado.getNomeSalgado());
        holder.descricao.setText(salgado.getDescricao());
        holder.valor.setText(strValue);
        holder.delete_item.setOnClickListener(view -> {
            deletar(salgado.getId(), view.getContext());
            removeItem(position);
        });
    }

    private void removeItem(int position) {
        salgadoList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, salgadoList.size()); //Esse cara que atualiza o recycler
    }

    @Override
    public int getItemCount() { //vai adicionar novos items, dependendo de quantos itens estiverem no nosso contrutor
        return salgadoList.size();
    }

    @Override
    public long getItemId(int position) {
        Salgado salgado = salgadoList.get(position);
        return salgado.getId();
    }

    private void deletar(long id, Context context) {
        RetrofitService retrofitService = new RetrofitService();
        SalgadoAPI salgadoAPI = retrofitService.getRetrofit().create(SalgadoAPI.class);

        salgadoAPI.delete(id)
                .enqueue(new Callback<Salgado>() {
                    @Override
                    public void onResponse(Call<Salgado> call, Response<Salgado> response) {
                        Toast.makeText(context, "Deletou do jeito certo", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Salgado> call, Throwable t) {
                        Toast.makeText(context, "Deletou rápido dms", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static class SalgadoHolder extends RecyclerView.ViewHolder {

        final TextView nome;
        final TextView descricao;
        final TextView valor;
        final Button delete_item;

        public SalgadoHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.salgadoListItem_nome);
            descricao = itemView.findViewById(R.id.salgadoListItem_descricao);
            valor = itemView.findViewById(R.id.salgadoListItem_valor);
            delete_item = itemView.findViewById(R.id.doceDeleteBtn);
        }

    }
}