package com.lanchonete.funcionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lanchonete.Home;
import com.lanchonete.R;
import com.lanchonete.funcionario.get.bebida.BebidaListActivity;
import com.lanchonete.funcionario.get.doce.DoceListActivity;
import com.lanchonete.funcionario.get.funcionario.FuncionarioListActivity;
import com.lanchonete.funcionario.get.pedido.PedidoListActivity;
import com.lanchonete.funcionario.get.salgado.SalgadoListActivity;

public class MenuFuncionario extends AppCompatActivity {

    Button btnViewDoce, btnViewSalgados, btnViewBebidas, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_funcionario);

        Intent intent = getIntent();

        btnViewBebidas = findViewById(R.id.btnMenuBebidas);
        btnViewDoce = findViewById(R.id.btnMenuDoce);
        btnViewSalgados = findViewById(R.id.btnMenuSalgados);
        btnVoltar = findViewById(R.id.btnVoltarMenuFuncionario);

        btnViewBebidas.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), BebidaListActivity.class);
            startActivity(intent1);
        });

        btnViewDoce.setOnClickListener(v -> {
            Intent intent2 = new Intent(getApplicationContext(), DoceListActivity.class);
            startActivity(intent2);
        });

        btnViewSalgados.setOnClickListener(v -> {
            Intent intent5 = new Intent(getApplicationContext(), SalgadoListActivity.class);
            startActivity(intent5);
        });

        btnVoltar.setOnClickListener(v -> {
            Intent intent6 = new Intent(getApplicationContext(), Home.class);
            startActivity(intent6);
        });

    }
}