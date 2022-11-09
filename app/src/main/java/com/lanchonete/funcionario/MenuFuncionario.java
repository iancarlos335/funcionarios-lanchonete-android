package com.lanchonete.funcionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lanchonete.R;
import com.lanchonete.funcionario.get.bebida.BebidaListActivity;
import com.lanchonete.funcionario.get.doce.DoceListActivity;
import com.lanchonete.funcionario.get.funcionario.FuncionarioListActivity;
import com.lanchonete.funcionario.get.pedido.PedidoListActivity;
import com.lanchonete.funcionario.get.salgado.SalgadoListActivity;

public class MenuFuncionario extends AppCompatActivity {

    Button btnViewFuncionario, btnViewPedidos, btnViewDoce, btnViewSalgados, btnViewBebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_funcionario);

        Intent intent = getIntent();

        btnViewBebidas = findViewById(R.id.btnMenuBebidas);
        btnViewDoce = findViewById(R.id.btnMenuDoce);
        btnViewFuncionario = findViewById(R.id.btnMenuFuncionario);
        btnViewPedidos = findViewById(R.id.btnMenuPedidos);
        btnViewSalgados = findViewById(R.id.btnMenuSalgados);

        btnViewBebidas.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), BebidaListActivity.class);
            startActivity(intent1);
        });

        btnViewDoce.setOnClickListener(v -> {
            Intent intent2 = new Intent(getApplicationContext(), DoceListActivity.class);
            startActivity(intent2);
        });

        btnViewFuncionario.setOnClickListener(v -> {
            Intent intent3 = new Intent(getApplicationContext(), FuncionarioListActivity.class);
            startActivity(intent3);
        });

        btnViewPedidos.setOnClickListener(v -> {
            Intent intent4 = new Intent(getApplicationContext(), PedidoListActivity.class);
            startActivity(intent4);
        });
        btnViewSalgados.setOnClickListener(v -> {
            Intent intent5 = new Intent(getApplicationContext(), SalgadoListActivity.class);
            startActivity(intent5);
        });

    }
}