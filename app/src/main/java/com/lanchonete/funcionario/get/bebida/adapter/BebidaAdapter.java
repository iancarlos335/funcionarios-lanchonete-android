package com.lanchonete.funcionario.get.bebida.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.funcionario.get.bebida.BebidaListActivity;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BebidaAdapter extends RecyclerView.Adapter<BebidaHolder> {

    private Context context;

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

        holder.nome_bebida.setText(bebida.getNomeBebida());
        holder.descricao_bebida.setText(bebida.getDescricao());
        holder.valor_bebida.setText(strValue);
        holder.delete_item.setOnClickListener(view -> {
            bebidaList.remove(position);

            //Deletando
            RetrofitService retrofitService = new RetrofitService();
            BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);

            BebidaHolder bebidaHolder;

            bebidaAPI.delete(bebida.getId())
                    .enqueue(new Callback<Bebida>() {
                        @Override
                        public void onResponse(Call<Bebida> call, Response<Bebida> response) {
                            Toast.makeText(context.getApplicationContext(), "Deletado com sucesso", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Bebida> call, Throwable t) {
                            Toast.makeText(context.getApplicationContext(), "Não deletou nada", Toast.LENGTH_SHORT).show();
                        }
                    });
        });


    }

    @Override
    public long getItemId(int position) {
        Bebida bebida = bebidaList.get(position);
        return bebida.getId();
    }

    @Override
    public int getItemCount() { //vai adicionar novos items, dependendo de quantos itens estiverem no nosso contrutor
        return bebidaList.size();
    }
}
