package com.lanchonete.funcionario.get.bebida;

import android.content.Context;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lanchonete.R;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;

public class BebidaAdapter extends RecyclerView.Adapter<BebidaAdapter.BebidaHolder> implements View.OnClickListener, View.OnLongClickListener {


    private final LinkedList<Bebida> bebidaList;

    public BebidaAdapter(LinkedList<Bebida> bebidaList) {
        this.bebidaList = bebidaList;
    }

    @NonNull
    @Override
    public BebidaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bebidas_items, parent, false);

        return new BebidaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BebidaHolder holder, int position) {
        Bebida bebida = bebidaList.get(position);
        String strValue = Double.toString(bebida.getValor());

        holder.itemView.setOnLongClickListener(this);

        holder.nome_bebida.setText(bebida.getNomeBebida());
        holder.descricao_bebida.setText(bebida.getDescricao());
        holder.valor_bebida.setText(strValue);
        holder.delete_item.setOnClickListener(view -> deletar(bebida.getId(), view.getContext(), position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    private void removeItem(int position) {
        bebidaList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bebidaList.size()); //Esse cara que atualiza o recycler
    }


    @Override
    public int getItemCount() {
        return bebidaList.size();
    }

    private void deletar(long id, Context context, int position) {
        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);

        bebidaAPI.delete(id)
                .enqueue(new Callback<Bebida>() {
                    @Override
                    public void onResponse(Call<Bebida> call, Response<Bebida> response) {
                        removeItem(position);
                    }

                    @Override
                    public void onFailure(Call<Bebida> call, Throwable t) {
                        Toast.makeText(context, "Vai devagar, o item não foi removido", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public static class BebidaHolder extends RecyclerView.ViewHolder{

        final TextView nome_bebida;

        final TextView descricao_bebida;
        final TextView valor_bebida;
        final Button delete_item;


        public BebidaHolder(@NonNull View itemView) {
            super(itemView);

            nome_bebida = itemView.findViewById(R.id.bebidasListItem_nome);
            descricao_bebida = itemView.findViewById(R.id.bebidasListItem_descricao);
            valor_bebida = itemView.findViewById(R.id.bebidasListItem_valor);
            delete_item = itemView.findViewById(R.id.btnDeleteBebidas);
        }
    }
}

