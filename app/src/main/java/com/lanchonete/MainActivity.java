package com.lanchonete;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.time.format.DecimalStyle;

public class MainActivity extends AppCompatActivity {

    EditText editTextNomeBebida, editTextValor;
    TextView textViewTitulo;
    Button buttonBotao;

    private void iniciandoComponentes() {
        textViewTitulo = findViewById(R.id.titulo);
        buttonBotao = findViewById(R.id.botao);
        editTextNomeBebida = findViewById(R.id.nome_bebida);
        editTextValor = findViewById(R.id.valor);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciandoComponentes();

        buttonBotao.setOnClickListener(view -> {
            String nomeBebida = editTextNomeBebida.getText().toString();
            String strvalor = editTextValor.getText().toString();
            Double valor = Double.parseDouble(strvalor); //só vou usar esse cara



        });
    }




/*
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), SegundaPagina.class);
                    }
                });

            }
*/

    }


