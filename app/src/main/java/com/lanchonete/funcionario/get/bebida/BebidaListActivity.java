package com.lanchonete.funcionario.get.bebida;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lanchonete.R;
import com.lanchonete.funcionario.post.BebidaActivity;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;
import java.util.List;

public class BebidaListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ImageView irInicio;
    Button buttonAddBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida_list);

        recyclerView = findViewById(R.id.bebidasList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonAddBebida = findViewById(R.id.btnAdicionarNovaBebida);
        irInicio = findViewById(R.id.imageButtonVoltarInicioBebida);

        carregar();

        irInicio.setOnClickListener(v -> finish());


        buttonAddBebida.setOnClickListener(v -> {
            Intent intentGoBebidaActivity = new Intent(getApplicationContext(), BebidaActivity.class);
            startActivity(intentGoBebidaActivity);
        });
    }

    @Override
    protected void onRestart() {
        carregar();
        super.onRestart();
    }

    private void carregar() { //TODO change object method (from another class)
        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);
        // Reading
        bebidaAPI.listBebida()
                .enqueue(new Callback<List<Bebida>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Bebida>> call, @NonNull Response<List<Bebida>> response) {
                        assert response.body() != null;
                        LinkedList<Bebida> bebidas = new LinkedList<>(response.body());
                        preencherListView(bebidas);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Bebida>> call, @NonNull Throwable t) {
                        Toast.makeText(BebidaListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void preencherListView(LinkedList<Bebida> bebidaList) {
        BebidaAdapter bebidaAdapter = new BebidaAdapter(bebidaList);
        recyclerView.setAdapter(bebidaAdapter);
    }

}