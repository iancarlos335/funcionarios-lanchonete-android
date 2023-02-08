package com.lanchonete.funcionario.get.bebida;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lanchonete.R;
import com.lanchonete.funcionario.MenuFuncionario;
import com.lanchonete.funcionario.get.helper.RecyclerItemClickListener;
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

    LinkedList<Bebida> bebidas = new LinkedList<>();
    private RecyclerView recyclerView;
    ImageView irInicio;
    Button buttonAddBebida;
    private ActionMode actionMode;
    private BebidaAdapter bebidaAdapter;

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

    private void carregar() {

        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);
        // Reading
        bebidaAPI.listBebida()
                .enqueue(new Callback<List<Bebida>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Bebida>> call, @NonNull Response<List<Bebida>> response) {
                        assert response.body() != null;
                        bebidas.addAll(response.body());
                        bebidaAdapter = new BebidaAdapter(bebidas);
                        preencherListView(bebidaAdapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Bebida>> call, @NonNull Throwable t) {
                        Toast.makeText(BebidaListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void preencherListView(BebidaAdapter adapter) {
        recyclerView.setAdapter(adapter);

        adapter.setListener(new BebidaAdapter.BebidaAdapterListener() {
            @Override
            public void onItemClick(int position) {
                enableActionMode(position, adapter);
            }

            @Override
            public void onItemLongClick(int position) {
                enableActionMode(position, adapter);
            }
        });
    }

    public void enableActionMode(int position, BebidaAdapter adapter) {
//        if (actionMode == null) { // pq q isso n funciona vei?
        actionMode = startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    adapter.deletar(bebidaAdapter.getItemId(position), position);
                    mode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        adapter.toggleSelection(position);

        final int size = adapter.selectedItems.size();
        if (size == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(size + "");
            actionMode.invalidate();
        }
    }

//    }


}