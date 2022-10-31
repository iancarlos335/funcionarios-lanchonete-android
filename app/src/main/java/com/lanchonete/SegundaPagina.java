package com.lanchonete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SegundaPagina extends AppCompatActivity {

    Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_pagina);
        botao = findViewById(R.id.buttonSegundaPagina);

        botao.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityExample.class);
            startActivity(intent);
        });

    }
}