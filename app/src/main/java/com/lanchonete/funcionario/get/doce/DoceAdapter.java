package com.lanchonete.funcionario.get.doce;

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
import com.lanchonete.model.Bebida;
import com.lanchonete.model.Doce;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.DoceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;
import java.util.List;

public class DoceAdapter extends RecyclerView.Adapter<DoceAdapter.DoceHolder> {

    private final LinkedList<Doce> doceList;

    public DoceAdapter(LinkedList<Doce> doceList) {
        this.doceList = doceList;
    }

    @NonNull
    @Override
    public DoceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_doces_itens, parent, false);
        return new DoceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoceHolder holder, int position) { //É esse position que armazena cada item pelo id
        Doce doce = doceList.get(position);
        String strValue = Double.toString(doce.getValor());

        holder.nome.setText(doce.getNomeDoce());
        holder.descricao.setText(doce.getDescricao());
        holder.valor.setText(strValue);
        holder.delete_item.setOnClickListener(view -> deletar(doce.getId(), view.getContext(), position));


    }

    @Override
    public int getItemCount() {
        return doceList.size();
    }

    @Override
    public long getItemId(int position) {
        Doce doce = doceList.get(position);
        return doce.getId();
    }

    private void removeItem(int position) {
        doceList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, doceList.size()); //Esse cara que atualiza o recycler
    }

    private void deletar(long id, Context context, int position) {
        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class);

        doceAPI.delete(id)
                .enqueue(new Callback<Doce>() {
                    @Override
                    public void onResponse(Call<Doce> call, Response<Doce> response) {
                        removeItem(position);
                    }

                    @Override
                    public void onFailure(Call<Doce> call, Throwable t) {
                        Toast.makeText(context, "Vai devagar, o item não foi removido", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static class DoceHolder extends RecyclerView.ViewHolder {

        final TextView nome;
        final TextView descricao;
        final TextView valor;
        final Button delete_item;

        public DoceHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.doceListItem_nome);
            descricao = itemView.findViewById(R.id.doceListItem_descricao);
            valor = itemView.findViewById(R.id.doceListItem_valor);
            delete_item = itemView.findViewById(R.id.doceDeleteBtn);

        }
    }


}
