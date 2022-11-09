package com.lanchonete.funcionario.post.bebida.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;

public class BebidaImagemHolder extends RecyclerView.ViewHolder {

    final ImageButton imagemCoca, imagemFanta, imagemSprite, imagemPepsi;
    final RadioButton radioCoca, radioFanta, radioSprite, radioPepsi;

    public BebidaImagemHolder(@NonNull View itemView) {
        super(itemView);
        imagemCoca = itemView.findViewById(R.id.imageButtonCoca);
        imagemFanta = itemView.findViewById(R.id.imageButtonFanta);
        imagemSprite = itemView.findViewById(R.id.imageButtonSprite);
        imagemPepsi = itemView.findViewById(R.id.imageButtonPepsi);

        radioCoca = itemView.findViewById(R.id.radioButtonCoca);
        radioFanta = itemView.findViewById(R.id.radioButtonFanta);
        radioSprite = itemView.findViewById(R.id.radioButtonSprite);
        radioPepsi = itemView.findViewById(R.id.radioButtonPepsi);
    }



}
