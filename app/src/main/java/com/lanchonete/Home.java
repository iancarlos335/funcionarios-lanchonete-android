package com.lanchonete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lanchonete.funcionario.MenuFuncionario;

public class Home extends AppCompatActivity {

    Button btnGoMenu, btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        btnGoMenu = findViewById(R.id.btnGoMenu);
        btnSair = findViewById(R.id.btnSairFuncionarios);

        btnGoMenu.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), MenuFuncionario.class);
            startActivity(intent1);
        });

        btnSair.setOnClickListener(v -> {
            Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
        });
    }
}