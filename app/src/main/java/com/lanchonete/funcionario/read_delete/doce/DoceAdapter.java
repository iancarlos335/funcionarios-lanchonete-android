package com.lanchonete.funcionario.read_delete.doce;

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
import com.lanchonete.model.Doce;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.DoceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;

public class DoceAdapter extends RecyclerView.Adapter<DoceAdapter.DoceHolder> {

    private final LinkedList<Doce> doceList;
    private DoceAdapterListener listener;
    final SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int currentSelectedPosition;

    public DoceAdapter(LinkedList<Doce> doceList) {
        this.doceList = doceList;
    }

    public void setListener(DoceAdapterListener listener) {
        this.listener = listener;
    }

    public LinkedList<Doce> getDoces() {
        return doceList;
    }


    @NonNull
    @Override
    public DoceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_doces_itens, parent, false);
        return new DoceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoceHolder holder, int position) {
        Doce doce = doceList.get(position);
        holder.bindHolder(doce);

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
    public int getItemCount() {
        return doceList.size();
    }

    @Override
    public long getItemId(int position) {
        Doce doce = doceList.get(position);
        return doce.getId();
    }

    public void toggleSelection(int position) {
        currentSelectedPosition = position;
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
            doceList.get(position).setSelected(false);
        } else {
            doceList.get(position).setSelected(true);
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    private void removeItem(LinkedList<Doce> deletedItems) {
        doceList.removeAll(deletedItems);
        if (doceList.size() > 0) {
            notifyItemRangeRemoved(doceList.indexOf(doceList.getFirst()), doceList.size());
            notifyItemRangeChanged(doceList.indexOf(doceList.getFirst()), doceList.size());
        }
    }

    protected void deletar(long id, LinkedList<Doce> deletedItems) {
        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class);

        doceAPI.delete(id)
                .enqueue(new Callback<Doce>() {
                    @Override
                    public void onResponse(Call<Doce> call, Response<Doce> response) {
                        removeItem(deletedItems);
                    }

                    @Override
                    public void onFailure(Call<Doce> call, Throwable t) {
                        Log.i("SEVERE", "Não deletou");
                    }
                });
    }

    public static class DoceHolder extends RecyclerView.ViewHolder {

        final TextView nome;
        final TextView descricao;
        final TextView valor;

        public DoceHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.docesListItem_nome);
            descricao = itemView.findViewById(R.id.docesListItem_descricao);
            valor = itemView.findViewById(R.id.docesListItem_valor);
        }

        public void bindHolder(Doce doce) {
            String strValue = Double.toString(doce.getValor());

            nome.setText(doce.getNome());
            descricao.setText(doce.getDescricao());
            valor.setText(strValue);

            if (doce.isSelected()) {
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

    interface DoceAdapterListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }
}
