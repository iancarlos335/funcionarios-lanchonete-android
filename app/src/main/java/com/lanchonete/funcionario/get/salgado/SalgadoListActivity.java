package com.lanchonete.funcionario.get.salgado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanchonete.R;


import com.lanchonete.funcionario.MenuFuncionario;
import com.lanchonete.funcionario.get.salgado.adapter.SalgadoAdapter;
import com.lanchonete.funcionario.post.SalgadoActivity;
import com.lanchonete.model.Doce;
import com.lanchonete.model.Salgado;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.SalgadoAPI;


import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalgadoListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ImageView irInicio;
    Button buttonAddSalgado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salgado_list);

        recyclerView = findViewById(R.id.salgadosList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarSalgados();

        buttonAddSalgado = findViewById(R.id.btnAdicionarNovoSalgado);
        irInicio = findViewById(R.id.imageButtonVoltarInicioSalgado);

        irInicio.setOnClickListener(v -> finish());

        buttonAddSalgado.setOnClickListener(v -> {
            Intent intentGoBebidaActivity = new Intent(getApplicationContext(), SalgadoActivity.class);
            startActivity(intentGoBebidaActivity);
        });
    }

    @Override
    protected void onRestart() {
        carregarSalgados();
        super.onRestart();
    }

    public void carregarSalgados() {
        RetrofitService retrofitService = new RetrofitService();
        SalgadoAPI salgadoAPI = retrofitService.getRetrofit().create(SalgadoAPI.class);
        salgadoAPI.listSalgado()
                .enqueue(new Callback<List<Salgado>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Salgado>> call, @NonNull Response<List<Salgado>> response) {
                        assert response.body() != null;
                        LinkedList<Salgado> salgados = new LinkedList<>(response.body());
                        preencherListView(salgados);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Salgado>> call, @NonNull Throwable t) {
                        Toast.makeText(SalgadoListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void preencherListView(LinkedList<Salgado> salgadoList) {
        SalgadoAdapter salgadoAdapter = new SalgadoAdapter(salgadoList);
        recyclerView.setAdapter(salgadoAdapter);
    }

}