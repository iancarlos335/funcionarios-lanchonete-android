package com.lanchonete.funcionario.get.doce;

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
import com.lanchonete.funcionario.post.DoceActivity;
import com.lanchonete.model.Bebida;
import com.lanchonete.model.Doce;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.DoceAPI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ImageView irInicio;
    Button buttonAddDoce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doce_list);

        recyclerView = findViewById(R.id.docesList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonAddDoce = findViewById(R.id.btnAdicionarNovoDoce);
        irInicio = findViewById(R.id.imageButtonVoltarInicioDoce);

        carregarDoces();

        irInicio.setOnClickListener(v -> finish());

        buttonAddDoce.setOnClickListener(v -> {
            Intent intentGoBebidaActivity = new Intent(getApplicationContext(), DoceActivity.class);
            startActivity(intentGoBebidaActivity);
        });
    }

    @Override
    protected void onRestart() {
        carregarDoces();
        super.onRestart();
    }

    private void carregarDoces() {
        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class);

        doceAPI.listDoce()
                .enqueue(new Callback<List<Doce>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Doce>> call, @NonNull Response<List<Doce>> response) {
                        assert response.body() != null;
                        LinkedList<Doce> doces = new LinkedList<>(response.body());
                        preencherListView(doces);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Doce>> call, @NonNull Throwable t) {
                        Toast.makeText(DoceListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void preencherListView(LinkedList<Doce> doceList) {
        DoceAdapter doceAdapter = new DoceAdapter(doceList);
        recyclerView.setAdapter(doceAdapter);
    }

}