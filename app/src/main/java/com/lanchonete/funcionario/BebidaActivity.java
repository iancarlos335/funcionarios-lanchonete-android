package com.lanchonete.funcionario;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lanchonete.R;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BebidaActivity extends AppCompatActivity {

    EditText editTextNomeBebida, editTextValor, editTextDescricao, editTextImagem;
    Button buttonBotao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebida_activity);

        iniciandoComponentes();
    }

    private void iniciandoComponentes() {
        buttonBotao = findViewById(R.id.botao_bebida);
        editTextNomeBebida = findViewById(R.id.nome_bebida);
        editTextValor = findViewById(R.id.valor_bebida);
        editTextDescricao = findViewById(R.id.descricao_bebida); //adicionar input direito
        editTextImagem = findViewById(R.id.imagem_bebida);


        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class); //ele vai gerar uma nova requisição http, e nesse caso do tipo POST

        buttonBotao.setOnClickListener(view -> {
            String nomeBebida = editTextNomeBebida.getText().toString();
            String descricao = editTextDescricao.getText().toString();
            String strvalor = editTextValor.getText().toString();
            double valor = Double.parseDouble(strvalor); //só vou usar esse cara
            String imgBebida = editTextImagem.getText().toString();

            Bebida bebida = new Bebida();
            bebida.setNomeBebida(nomeBebida);
            bebida.setValor(valor);
            bebida.setDescricao(descricao);
            bebida.setImagem(imgBebida);

            bebidaAPI.addBebida(bebida) //chama o método POST
                    .enqueue(new Callback<Bebida>() { //deixa as requisições em fila
                        @Override
                        public void onResponse(Call<Bebida> call, Response<Bebida> response) {
                            Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Bebida> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Não salvou!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(BebidaActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                        }
                    });
        });
    }
}


