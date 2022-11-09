package com.lanchonete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lanchonete.funcionario.MenuFuncionario;

public class Home extends AppCompatActivity {

    Button btnGoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        btnGoMenu = findViewById(R.id.btnGoMenu);

        btnGoMenu.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), MenuFuncionario.class);
            startActivity(intent1);
        });
    }
}