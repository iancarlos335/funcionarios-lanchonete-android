package com.lanchonete.funcionario.read_delete.salgado;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lanchonete.R;
import com.lanchonete.model.Salgado;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.SalgadoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;

public class SalgadoAdapter extends RecyclerView.Adapter<SalgadoAdapter.SalgadoHolder> {

    private final LinkedList<Salgado> salgadoList;
    private SalgadoAdapterListener listener;
    private int currentSelectedPosition;
    final SparseBooleanArray selectedItems = new SparseBooleanArray();


    public SalgadoAdapter(LinkedList<Salgado> salgadoList) {
        this.salgadoList = salgadoList;
    }

    public void setListener(SalgadoAdapterListener listener) {
        this.listener = listener;
    }

    public LinkedList<Salgado> getSalgados() {
        return salgadoList;
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
        holder.bindHolder(salgado);

        holder.itemView.setOnClickListener(v -> {
            if (salgadoList.size() > 0 && listener != null) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onItemLongClick(holder.getAdapterPosition());
            }
            return true;
        });

        if (currentSelectedPosition == position) {
            currentSelectedPosition = -1;
        }
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

    public void toggleSelection(int position) {
        currentSelectedPosition = position;
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
            salgadoList.get(position).setSelected(false);
        } else {
            salgadoList.get(position).setSelected(true);
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    private void removeItem(LinkedList<Salgado> deletedItems) {
        salgadoList.removeAll(deletedItems);
        if (salgadoList.size() > 0) {
            notifyItemRangeRemoved(salgadoList.indexOf(salgadoList.getFirst()), salgadoList.size());
            notifyItemRangeChanged(salgadoList.indexOf(salgadoList.getFirst()), salgadoList.size());
        }
    }

    protected void deletar(long id, LinkedList<Salgado> deletedItems) {
        RetrofitService retrofitService = new RetrofitService();
        SalgadoAPI salgadoAPI = retrofitService.getRetrofit().create(SalgadoAPI.class);

        salgadoAPI.delete(id)
                .enqueue(new Callback<Salgado>() {
                    @Override
                    public void onResponse(Call<Salgado> call, Response<Salgado> response) {
                        removeItem(deletedItems);
                    }

                    @Override
                    public void onFailure(Call<Salgado> call, Throwable t) {
                        Log.i("SEVERE", "Não deletou");
                    }
                });
    }

    public static class SalgadoHolder extends RecyclerView.ViewHolder {

        final TextView nome;
        final TextView descricao;
        final TextView valor;

        public SalgadoHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.salgadosListItem_nome);
            descricao = itemView.findViewById(R.id.salgadosListItem_descricao);
            valor = itemView.findViewById(R.id.salgadosListItem_valor);
        }

        public void bindHolder(Salgado salgado) {
            String strValue = Double.toString(salgado.getValor());

            nome.setText(salgado.getNome());
            descricao.setText(salgado.getDescricao());
            valor.setText(strValue);

            if (salgado.isSelected()) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(32f);
                gradientDrawable.setColor(Color.rgb(232, 240, 253));
                itemView.setBackground(gradientDrawable);
            } else {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(32f);
                gradientDrawable.setColor(Color.WHITE);
                itemView.setBackground(gradientDrawable);
            }
        }

    }

    interface SalgadoAdapterListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }
}