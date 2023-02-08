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

                        recyclerView.setAdapter(bebidaAdapter); // Eu não encontrei lugar melhor infelizamente
                        bebidaAdapter.setListener(new BebidaAdapter.BebidaAdapterListener() {
                            @Override
                            public void onItemClick(int position) {
                                enableActionMode(position);
                            }

                            @Override
                            public void onItemLongClick(int position) {
                                enableActionMode(position);
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Bebida>> call, @NonNull Throwable t) {
                        Toast.makeText(BebidaListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void enableActionMode(int position) {
        if (actionMode == null) { // that if without brackets annoyed we very well
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
                        LinkedList<Bebida> bebidaList = bebidaAdapter.getBebidas();
                        for (Bebida bebida : bebidaList) {
                            if (bebida.isSelected()) {
                                bebida.setSelected(false);
                                bebidaAdapter.deletar(bebida.getId(), bebidaList.indexOf(bebida));
                            }
                        }
                        mode.finish();
                        return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    bebidaAdapter.selectedItems.clear();
                    LinkedList<Bebida> bebidaList = bebidaAdapter.getBebidas();
                    for (Bebida bebida : bebidaList) {
                        if (bebida.isSelected()) {
                            bebida.setSelected(false);
                        }
                    }

                    bebidaAdapter.notifyItemRangeChanged(bebidaList.indexOf(bebidaList.getFirst()), bebidaList.size());
                    actionMode = null;
                }

            });
        }

        bebidaAdapter.toggleSelection(position);

        final int size = bebidaAdapter.selectedItems.size();
        if (size == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(size + "");
            actionMode.invalidate();
        }

    }


}