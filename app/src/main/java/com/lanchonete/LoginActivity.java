package com.lanchonete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lanchonete.funcionario.FuncionarioMenu;

public class LoginActivity extends AppCompatActivity {

    Button btnMenuCliente, btnMenuFuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnMenuCliente = findViewById(R.id.btnMenuCliente);
        btnMenuFuncionario = findViewById(R.id.btnMenuFuncionario);

        btnMenuCliente.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FuncionarioMenu.class);
            startActivity(intent);
        });

        btnMenuFuncionario.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FuncionarioMenu.class);
            startActivity(intent);
        });
    }
}