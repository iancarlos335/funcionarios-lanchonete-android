package com.lanchonete.funcionario.get;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.funcionario.get.adapter.BebidaAdapter;
import com.lanchonete.funcionario.get.adapter.BebidaHolder;
import com.lanchonete.funcionario.post.BebidaActivity;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BebidaListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    Button buttonAddBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida_list);

        Intent intent = getIntent();

        //Intent intentGoBebidaListActivity = getIntent();

        recyclerView = findViewById(R.id.bebidasList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarBebidas();

        buttonAddBebida = findViewById(R.id.btnAdicionarNovaBebida);
        buttonAddBebida.setOnClickListener(v -> {
           Intent intentGoBebidaActivity = new Intent(getApplicationContext(), BebidaActivity.class);
           startActivity(intentGoBebidaActivity);
        });


    }

    public void carregarBebidas() {
        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);
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
        BebidaAdapter bebidaAdapter = new BebidaAdapter(bebidaList);
        recyclerView.setAdapter(bebidaAdapter);
    }

    private void selecionarPeloId(List<Bebida> bebidaList) {
        //View
        //BebidaHolder bebidaHolder = new BebidaHolder();
        BebidaAdapter bebidaAdapter = new BebidaAdapter(bebidaList);
        //recyclerView.getChildItemId()
    }

}