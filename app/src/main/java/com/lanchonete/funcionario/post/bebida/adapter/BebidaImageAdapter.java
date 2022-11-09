package com.lanchonete.funcionario.post.bebida.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.model.Bebida;

import java.util.List;

public class BebidaImageAdapter extends RecyclerView.Adapter<BebidaImagemHolder> {

    private final List<Bebida> bebidaList;

    public BebidaImageAdapter(List<Bebida> bebidaList) {
        this.bebidaList = bebidaList;
    }

    @NonNull
    public BebidaImagemHolder onCreateView(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bebida_imagens, parent, false); //esse macete do binding.getRoot() talvez me salve
        return new BebidaImagemHolder(view);
    }

    @NonNull
    @Override
    public BebidaImagemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BebidaImagemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
