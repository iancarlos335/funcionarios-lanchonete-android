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
import com.lanchonete.funcionario.MenuFuncionario;
import com.lanchonete.funcionario.get.doce.adapter.DoceAdapter;
import com.lanchonete.funcionario.post.DoceActivity;
import com.lanchonete.model.Doce;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.DoceAPI;

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

        Intent intent = getIntent();

        //Intent intentGoBebidaListActivity = getIntent();

        recyclerView = findViewById(R.id.docesList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarDoces();

        buttonAddDoce = findViewById(R.id.btnAdicionarNovoDoce);
        irInicio = findViewById(R.id.imageButtonVoltarInicioDoce);

        irInicio.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), MenuFuncionario.class);
            startActivity(intent1);
        });

        buttonAddDoce.setOnClickListener(v -> {
            Intent intentGoBebidaActivity = new Intent(getApplicationContext(), DoceActivity.class);
            startActivity(intentGoBebidaActivity);
        });


    }

    public void carregarDoces() {
        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class);
        doceAPI.listDoce()
                .enqueue(new Callback<List<Doce>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Doce>> call, @NonNull Response<List<Doce>> response) {
                        preencherListView(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Doce>> call, @NonNull Throwable t) {
                        Toast.makeText(DoceListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void preencherListView(List<Doce> doceList) {
        DoceAdapter doceAdapter = new DoceAdapter(doceList);
        recyclerView.setAdapter(doceAdapter);
    }

    private void selecionarPeloId(List<Doce> doceList) {
        //View
        //BebidaHolder bebidaHolder = new BebidaHolder();
        DoceAdapter bebidaAdapter = new DoceAdapter(doceList);
        //recyclerView.getChildItemId()
    }

}