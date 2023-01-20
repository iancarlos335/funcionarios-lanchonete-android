package com.lanchonete.funcionario.get.bebida;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.funcionario.MenuFuncionario;
import com.lanchonete.funcionario.get.bebida.adapter.BebidaAdapter;
import com.lanchonete.funcionario.get.bebida.adapter.BebidaHolder;
import com.lanchonete.funcionario.post.BebidaActivity;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BebidaListActivity extends AppCompatActivity {

    private List<Bebida> bebidas = new ArrayList<>();

    private RecyclerView recyclerView;
    ImageView irInicio;
    Button buttonAddBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida_list);

        recyclerView = findViewById(R.id.bebidasList_recyclerView);
        buttonAddBebida = findViewById(R.id.btnAdicionarNovaBebida);
        irInicio = findViewById(R.id.imageButtonVoltarInicioBebida);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarBebidas();

        //mto zika

        irInicio.setOnClickListener(v -> {
            finish();
        });

        //has to be here

        buttonAddBebida.setOnClickListener(v -> {
            Intent intentGoBebidaActivity = new Intent(getApplicationContext(), BebidaActivity.class);
            startActivity(intentGoBebidaActivity);
        });


    }

    @Override
    protected void onRestart() {
        carregarBebidas();
        super.onRestart();
    }

    public void carregarBebidas() { //esse método ta certo
        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);
        // Reading
        bebidaAPI.listBebida()
                .enqueue(new Callback<List<Bebida>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Bebida>> call, @NonNull Response<List<Bebida>> response) {
                        preencherListView(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Bebida>> call, @NonNull Throwable t) {
                        Toast.makeText(BebidaListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private void preencherListView(List<Bebida> bebidaList) {
        bebidas.addAll(bebidaList);
        BebidaAdapter bebidaAdapter = new BebidaAdapter(bebidaList);
        recyclerView.setAdapter(bebidaAdapter);
    }

    private void selecionarPeloId() {
        Bebida bebida = new Bebida();
        BebidaAdapter adapter = new BebidaAdapter(bebidas);
        int bebidaId = Integer.parseInt(String.valueOf(bebida.getId()));

        adapter.getItemId(bebidaId);
        recyclerView.setAdapter(adapter);
    }

}