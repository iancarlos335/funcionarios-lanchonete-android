package com.lanchonete.ui.bebidas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lanchonete.R;
import com.lanchonete.databinding.ActivityBebidaListBinding;
import com.lanchonete.databinding.FragmentDocesBinding;
import com.lanchonete.funcionario.get.adapter.BebidaAdapter;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;
import com.lanchonete.ui.doces.DocesViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BebidaListActivity extends AppCompatActivity {

    private ActivityBebidaListBinding binding;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida_list);

        recyclerView = findViewById(R.id.bebidasList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarBebidas();
    }

    public void carregarBebidas() {
        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);
        bebidaAPI.listBebida()
                .enqueue(new Callback<List<Bebida>>() {
                    @Override
                    public void onResponse(Call<List<Bebida>> call, Response<List<Bebida>> response) {
                        preencherListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Bebida>> call, Throwable t) {
                        Toast.makeText(BebidaListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void preencherListView(List<Bebida> bebidaList) {
        BebidaAdapter bebidaAdapter = new BebidaAdapter(bebidaList);
        recyclerView.setAdapter(bebidaAdapter);
    }

}