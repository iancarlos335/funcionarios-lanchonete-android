package com.lanchonete.funcionario.read_delete.bebida;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lanchonete.R;
import com.lanchonete.funcionario.create_update.BebidaActivity;
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
    LinkedList<Bebida> bebidas = new LinkedList<>();
    private ActionMode actionMode;
    private BebidaAdapter bebidaAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida_list);

        recyclerView = findViewById(R.id.bebidasList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        buttonAddBebida = findViewById(R.id.btnAdicionarNovaBebida);
        irInicio = findViewById(R.id.imageButtonVoltarInicioBebida);

        carregar();

        irInicio.setOnClickListener(v -> finish());

        buttonAddBebida.setOnClickListener(v -> startActivityForResult(new Intent(this, BebidaActivity.class), 1));


        swipeRefreshLayout = findViewById(R.id.bebidasSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            bebidas.clear();

            carregar();
            bebidaAdapter.notifyDataSetChanged(); // vai ser isso por enquanto, kkk TODO melhorar isso dps pelo amor
            swipeRefreshLayout.setRefreshing(false);
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bebida bebida = new Bebida();
        String[] bebidaArray;

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            bebidaArray = data.getStringArrayExtra("bebidaArray");


            bebida.setId(Integer.parseInt(bebidaArray[0]));
            bebida.setNome(bebidaArray[1]);
            bebida.setValor(Double.parseDouble(bebidaArray[2]));
            bebida.setDescricao(bebidaArray[3]);
            bebida.setImagem(bebidaArray[4]);
            bebida.setSelected(Boolean.getBoolean(bebidaArray[5]));

            bebidaAdapter.getBebidas().add(bebida);
            bebidaAdapter.notifyItemInserted(bebidaAdapter.getBebidas().indexOf(bebidaAdapter.getBebidas().getLast()));
            onRestart();
        }
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
                        preencherRecyclerView();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Bebida>> call, @NonNull Throwable t) {
                        Toast.makeText(BebidaListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void preencherRecyclerView() {
        recyclerView.setAdapter(bebidaAdapter);

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


    private void enableActionMode(int position) {
        if (actionMode == null) { // that if was without brackets, and annoyed we a little

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
                        LinkedList<Bebida> deletedItems = new LinkedList<>();
                        LinkedList<Bebida> adapterList = new LinkedList<>(bebidaAdapter.getBebidas());
                        for (Bebida bebida : adapterList) {
                            if (bebida.isSelected()) {
                                deletedItems.add(bebida);
                                bebidaAdapter.deletar(bebida.getId(), deletedItems); //isso é uma mega evolução
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
                    LinkedList<Bebida> adapterList = new LinkedList<>(bebidaAdapter.getBebidas());
                    for (Bebida bebida : adapterList) {
                        if (bebida.isSelected()) {
                            bebida.setSelected(false);
                        }
                    }

                    bebidaAdapter.notifyItemRangeChanged(position, adapterList.size());
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