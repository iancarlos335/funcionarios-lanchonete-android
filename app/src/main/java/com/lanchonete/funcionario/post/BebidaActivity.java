package com.lanchonete.funcionario.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lanchonete.R;
import com.lanchonete.funcionario.get.BebidaListActivity;
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
    Button btnBotao;
    ImageButton btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebida_activity);
        btnVoltar = findViewById(R.id.btnVoltar);

        Intent intent = getIntent();

        iniciandoComponentes();

        btnVoltar.setOnClickListener(view -> {
            Intent intentGoBebidaListActivity = new Intent(getApplicationContext(), BebidaListActivity.class);
            startActivity(intentGoBebidaListActivity);
        });
    }

    private void iniciandoComponentes() {
        btnBotao = findViewById(R.id.botao_bebida);
        editTextNomeBebida = findViewById(R.id.nome_bebida);
        editTextValor = findViewById(R.id.valor_bebida);
        editTextDescricao = findViewById(R.id.descricao_bebida); //adicionar input direito
        editTextImagem = findViewById(R.id.imagem_bebida);

        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class); //ele vai gerar uma nova requisição http, e nesse caso do tipo POST


        btnBotao.setOnClickListener(view -> {
            String nomeBebida = editTextNomeBebida.getText().toString().trim();
            String descricao = editTextDescricao.getText().toString().trim();
            String strvalor = editTextValor.getText().toString().trim();
            double valor = Double.parseDouble(strvalor); //só vou usar esse cara
            String imgBebida = editTextImagem.getText().toString();

            if (TextUtils.isEmpty(nomeBebida) || TextUtils.isEmpty(descricao) || TextUtils.isEmpty(strvalor)) {
                editTextNomeBebida.setError("Esse campo é obrigatório");
                editTextDescricao.setError("Esse campo é obrigatório");
                editTextValor.setError("Esse campo é obrigatório");
            } else {

                Bebida bebida = new Bebida();
                bebida.setNomeBebida(nomeBebida);
                bebida.setValor(valor);
                bebida.setDescricao(descricao);
                bebida.setImagem(imgBebida);

                bebidaAPI.addBebida(bebida) //chama o método POST
                        .enqueue(new Callback<Bebida>() { //deixa as requisições em fila
                            @Override
                            public void onResponse(@NonNull Call<Bebida> call, @NonNull Response<Bebida> response) {
                                Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                                Intent intentGoBebidaListActivity = new Intent(getApplicationContext(), BebidaListActivity.class);
                                startActivity(intentGoBebidaListActivity);
                            }

                            @Override
                            public void onFailure(@NonNull Call<Bebida> call, @NonNull Throwable t) {
                                Toast.makeText(getApplicationContext(), "Não salvou!!", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(BebidaActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                            }
                        });
            }
        });


    }

}
/*
    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imagem_bebida.setImageBitmap(bitmap);
                Toast.makeText(imagem_bebida.getContext(), "Download deu certo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(imagem_bebida.getContext(), "Download deu erro!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

 */




