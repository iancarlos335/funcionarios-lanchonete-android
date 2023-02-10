package com.lanchonete.funcionario.get.bebida;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.provider.DocumentsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import java.util.List;

public class BebidaAdapter extends RecyclerView.Adapter<BebidaAdapter.BebidaHolder> {

    private BebidaAdapterListener listener;
    private final LinkedList<Bebida> bebidaList;
    final SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int currentSelectedPosition;


    public BebidaAdapter(LinkedList<Bebida> bebidaList) {
        this.bebidaList = bebidaList;
    }

    public void setListener(BebidaAdapterListener listener) {
        this.listener = listener;
    }

    public LinkedList<Bebida> getBebidas() {
        return bebidaList;
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
        holder.bindHolder(bebida);

        holder.itemView.setOnClickListener(v -> {
            if (selectedItems.size() > 0 && listener != null) {
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
    public long getItemId(int position) {
        return bebidaList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return bebidaList.size();
    }


    public void toggleSelection(int position) {
        currentSelectedPosition = position;
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
            bebidaList.get(position).setSelected(false);
        } else {
            bebidaList.get(position).setSelected(true);
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void removeItem(LinkedList<Bebida> deletedItems) {
        bebidaList.removeAll(deletedItems);
        if (bebidaList.size() > 0) {
            notifyItemRangeRemoved(bebidaList.indexOf(bebidaList.getFirst()), bebidaList.size());
            notifyItemRangeChanged(bebidaList.indexOf(bebidaList.getFirst()), bebidaList.size());

        }
    }

    protected void deletar(long id, LinkedList<Bebida> deletedItems) {
        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);

        bebidaAPI.delete(id)
                .enqueue(new Callback<Bebida>() {
                    @Override
                    public void onResponse(Call<Bebida> call, Response<Bebida> response) {
                        removeItem(deletedItems);
                    }

                    @Override
                    public void onFailure(Call<Bebida> call, Throwable t) {
                        Log.i("SEVERE", "Não deletou");
                    }
                });

    }

    public static class BebidaHolder extends RecyclerView.ViewHolder {

        final TextView nome_bebida;

        final TextView descricao_bebida;
        final TextView valor_bebida;


        public BebidaHolder(@NonNull View itemView) {
            super(itemView);

            nome_bebida = itemView.findViewById(R.id.bebidasListItem_nome);
            descricao_bebida = itemView.findViewById(R.id.bebidasListItem_descricao);
            valor_bebida = itemView.findViewById(R.id.bebidasListItem_valor);
        }

        public void bindHolder(Bebida bebida) {
            String strValue = Double.toString(bebida.getValor());

            nome_bebida.setText(bebida.getNome());
            descricao_bebida.setText(bebida.getDescricao());
            valor_bebida.setText(strValue);

            if (bebida.isSelected()) {
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

    interface BebidaAdapterListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }
}

