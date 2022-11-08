package com.lanchonete.funcionario.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.lanchonete.R;
import com.lanchonete.funcionario.get.BebidaListActivity;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BebidaActivity extends AppCompatActivity {

    TextInputLayout inputLayoutNomeBebida, inputLayoutValor, inputLayoutDescricao, inputLayoutImagem;
    EditText editTextNomeBebida, editTextValor, editTextDescricao, editTextImagem;
    Button btnBotao;
    ImageButton btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebida_activity);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnBotao = findViewById(R.id.botao_bebida);
        editTextNomeBebida = findViewById(R.id.nome_bebida);
        editTextValor = findViewById(R.id.valor_bebida);
        editTextDescricao = findViewById(R.id.descricao_bebida); //adicionar input direito
        editTextImagem = findViewById(R.id.imagem_bebida);

        //Aaaaaaa mannnnnnnn
        inputLayoutNomeBebida = findViewById(R.id.nome_bebidaInputLayout);
        inputLayoutValor = findViewById(R.id.valor_bebidaInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_bebidaInputLayout);
        inputLayoutImagem = findViewById(R.id.imagem_bebidaInputLayout);

        Intent intent = getIntent();

        btnVoltar.setOnClickListener(view -> {
            Intent intentGoBebidaListActivity = new Intent(getApplicationContext(), BebidaListActivity.class);
            startActivity(intentGoBebidaListActivity);
        });


        editTextNomeBebida.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertNomeBebida = s.toString();
                if (alertNomeBebida.length() != 0) {
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(alertNomeBebida);
                    boolean caracteresEspeciais = matcher.find();
                    if (caracteresEspeciais) {
                        inputLayoutNomeBebida.setError("Caracteres não permitidos!");
                        inputLayoutNomeBebida.setHelperText("");
                    } else {
                        inputLayoutNomeBebida.setHelperText("");
                        inputLayoutNomeBebida.setError("");
                    }
                } else {
                    inputLayoutNomeBebida.setHelperText("");
                    inputLayoutNomeBebida.setError("Campo Obrigatório");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        editTextValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertValue = s.toString();
                if (alertValue.length() != 0) {
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(alertValue);
                    boolean caracteresEspeciais = matcher.find();
                    if (!caracteresEspeciais) {
                        inputLayoutValor.setHelperText("");
                        //iniciandoComponentes();
                    } else {
                        inputLayoutValor.setHelperText("Evite letras maiúsculas");
                    }
                } else {
                    inputLayoutValor.setHelperText("Campo Obrigatório*");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextDescricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertDescricao = s.toString();
                if (alertDescricao.length() != 0) {
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(alertDescricao);
                    boolean caracteresEspeciais = matcher.find();
                    if (!caracteresEspeciais) {
                        inputLayoutDescricao.setHelperText("");
                        //iniciandoComponentes();
                    } else {
                        inputLayoutDescricao.setHelperText("Evite letras maiúsculas");
                    }
                } else {
                    inputLayoutDescricao.setHelperText("Campo Obrigatório*");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextImagem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertImagem = s.toString();
                if (alertImagem.length() != 0) {
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(alertImagem);
                    boolean caracteresEspeciais = matcher.find();
                    if (!caracteresEspeciais) {
                        inputLayoutImagem.setHelperText("");
                        //iniciandoComponentes();
                    } else {
                        inputLayoutImagem.setHelperText("Evite letras maiúsculas");
                    }
                } else {
                    inputLayoutImagem.setHelperText("Campo Obrigatório*");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void iniciandoComponentes() {


        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class); //ele vai gerar uma nova requisição http, e nesse caso do tipo POST

        btnBotao.setOnClickListener(v ->
        {
            String nomeBebida = editTextNomeBebida.getText().toString();
            String descricao = editTextDescricao.getText().toString();
            String strValor = editTextValor.getText().toString();
            String imgBebida = editTextImagem.getText().toString();
            double valor = Double.parseDouble(strValor); // se ele ta dando erro vo botar ele aqui, só uso ele nesse objeto msm

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
                            Intent intentGoBebidaListActivity = new Intent(getApplicationContext(), BebidaListActivity.class);
                            startActivity(intentGoBebidaListActivity);
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


