package com.lanchonete.funcionario.read_delete.doce;

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
import com.lanchonete.funcionario.create_update.DoceActivity;
import com.lanchonete.model.Doce;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.DoceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;
import java.util.List;

public class DoceListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ImageView irInicio;
    Button buttonAddDoce;
    LinkedList<Doce> doces = new LinkedList<>();
    private ActionMode actionMode;
    private DoceAdapter doceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doce_list);

        recyclerView = findViewById(R.id.docesList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        buttonAddDoce = findViewById(R.id.btnAdicionarNovoDoce);
        irInicio = findViewById(R.id.imageButtonVoltarInicioDoce);

        carregar();

        irInicio.setOnClickListener(v -> finish());

        buttonAddDoce.setOnClickListener(v -> startActivityForResult(new Intent(this, DoceActivity.class), 1));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Doce doce = new Doce();
        String[] doceArray;

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            doceArray = data.getStringArrayExtra("doceArray");


            doce.setId(Integer.parseInt(doceArray[0]));
            doce.setNome(doceArray[1]);
            doce.setValor(Double.parseDouble(doceArray[2]));
            doce.setDescricao(doceArray[3]);
            doce.setImagem(doceArray[4]);
            doce.setSelected(Boolean.getBoolean(doceArray[5]));

            doceAdapter.getDoces().add(doce);
            doceAdapter.notifyItemInserted(doceAdapter.getDoces().indexOf(doceAdapter.getDoces().getLast()));
            onRestart();
        }
    }

    private void carregar() {
        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class);

        doceAPI.listDoce()
                .enqueue(new Callback<List<Doce>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Doce>> call, @NonNull Response<List<Doce>> response) {
                        assert response.body() != null;
                        doces.addAll(response.body());
                        doceAdapter = new DoceAdapter(doces);
                        preencherRecyclerView();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Doce>> call, @NonNull Throwable t) {
                        Toast.makeText(DoceListActivity.this, "Falha ao pegar do banco", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void preencherRecyclerView() {
        recyclerView.setAdapter(doceAdapter);

        doceAdapter.setListener(new DoceAdapter.DoceAdapterListener() {
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
                        LinkedList<Doce> deletedItems = new LinkedList<>();
                        LinkedList<Doce> adapterList = new LinkedList<>(doceAdapter.getDoces());
                        for (Doce doce : adapterList) {
                            if (doce.isSelected()) {
                                deletedItems.add(doce);
                                doceAdapter.deletar(doce.getId(), deletedItems);
                            }
                        }

                        mode.finish();
                        return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    doceAdapter.selectedItems.clear();
                    LinkedList<Doce> adapterList = new LinkedList<>(doceAdapter.getDoces());
                    for (Doce doce : adapterList) {
                        if (doce.isSelected()) {
                            doce.setSelected(false);
                        }
                    }

                    doceAdapter.notifyItemRangeChanged(position, adapterList.size());
                    actionMode = null;
                }
            });
        }

        doceAdapter.toggleSelection(position);

        final int size = doceAdapter.selectedItems.size();
        if (size == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(size + "");
            actionMode.invalidate();
        }
    }

}