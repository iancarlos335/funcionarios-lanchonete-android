package com.lanchonete.funcionario.read_delete.salgado;

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
import com.lanchonete.R;
import com.lanchonete.funcionario.create_update.SalgadoActivity;
import com.lanchonete.model.Salgado;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.SalgadoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;
import java.util.List;

public class SalgadoListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ImageView irInicio;
    Button buttonAddSalgado;
    LinkedList<Salgado> salgados = new LinkedList<>();
    private ActionMode actionMode;
    private SalgadoAdapter salgadoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salgado_list);

        recyclerView = findViewById(R.id.salgadosList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        buttonAddSalgado = findViewById(R.id.btnAdicionarNovoSalgado);
        irInicio = findViewById(R.id.imageButtonVoltarInicioSalgado);

        carregar();

        irInicio.setOnClickListener(v -> finish());

        buttonAddSalgado.setOnClickListener(v -> startActivityForResult(new Intent(this, SalgadoActivity.class), 1));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Salgado salgado = new Salgado();
        String[] salgadoArray;

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            salgadoArray = data.getStringArrayExtra("salgadoArray");


            salgado.setId(Integer.parseInt(salgadoArray[0]));
            salgado.setNome(salgadoArray[1]);
            salgado.setValor(Double.parseDouble(salgadoArray[2]));
            salgado.setDescricao(salgadoArray[3]);
            salgado.setImagem(salgadoArray[4]);
            salgado.setSelected(Boolean.getBoolean(salgadoArray[5]));

            salgadoAdapter.getSalgados().add(salgado);
            salgadoAdapter.notifyItemInserted(salgadoAdapter.getSalgados().indexOf(salgadoAdapter.getSalgados().getLast()));
            onRestart();
        }
    }

    public void carregar() {
        RetrofitService retrofitService = new RetrofitService();
        SalgadoAPI salgadoAPI = retrofitService.getRetrofit().create(SalgadoAPI.class);
        salgadoAPI.listSalgado()
                .enqueue(new Callback<List<Salgado>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Salgado>> call, @NonNull Response<List<Salgado>> response) {
                        assert response.body() != null;
                        salgados.addAll(response.body());
                        salgadoAdapter = new SalgadoAdapter(salgados);
                        preencherRecyclerView();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Salgado>> call, @NonNull Throwable t) {
                        Toast.makeText(SalgadoListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void preencherRecyclerView() {
        recyclerView.setAdapter(salgadoAdapter);

        salgadoAdapter.setListener(new SalgadoAdapter.SalgadoAdapterListener() {
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
                        LinkedList<Salgado> deletedItems = new LinkedList<>();
                        LinkedList<Salgado> adapterList = new LinkedList<>(salgadoAdapter.getSalgados());
                        for (Salgado salgado : adapterList) {
                            if (salgado.isSelected()) {
                                deletedItems.add(salgado);
                                salgadoAdapter.deletar(salgado.getId(), deletedItems);
                            }
                        }

                        mode.finish();
                        return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    salgadoAdapter.selectedItems.clear();
                    LinkedList<Salgado> adapterList = new LinkedList<>(salgadoAdapter.getSalgados());
                    for (Salgado salgado : adapterList) {
                        if (salgado.isSelected()) {
                            salgado.setSelected(false);
                        }
                    }

                    salgadoAdapter.notifyItemRangeChanged(position, adapterList.size());
                    actionMode = null;
                }
            });
        }

        salgadoAdapter.toggleSelection(position);

        final int size = salgadoAdapter.selectedItems.size();
        if (size == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(size + "");
            actionMode.invalidate();
        }
    }

}