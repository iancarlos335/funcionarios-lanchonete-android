package com.lanchonete;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView mensagem;
    Button botao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mensagem = findViewById(R.id.mensagem);
        botao = findViewById(R.id.botao);

        botao.setOnClickListener(view -> {



            Intent intent = new Intent(getApplicationContext(), SegundaPagina.class);
            startService(intent);
            //Toast.makeText(getApplicationContext(), "Indo pra próxima página", Toast.LENGTH_SHORT).show();
        });





    }


}