package com.lanchonete.funcionario.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.lanchonete.R;


import com.lanchonete.funcionario.get.doce.DoceListActivity;
import com.lanchonete.model.Doce;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.DoceAPI;


import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoceActivity extends AppCompatActivity {

    TextInputLayout inputLayoutNomeDoce, inputLayoutValor, inputLayoutDescricao;
    EditText editTextNomeDoce, editTextValor, editTextDescricao;
    Button btnBotao;

    ImageButton btnVoltar, imagemCoca, imagemFanta, imagemSprite, imagemPepsi;
    RadioButton radioCoca, radioFanta, radioSprite, radioPepsi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doce_activity);

        btnVoltar = findViewById(R.id.btnDoceVoltar);
        btnBotao = findViewById(R.id.botao_doce);
        editTextNomeDoce = findViewById(R.id.nome_doce);
        editTextValor = findViewById(R.id.valor_doce);
        editTextDescricao = findViewById(R.id.descricao_doce); //adicionar input direito

        /*imagemCoca = findViewById(R.id.imageButtonCoca);
        imagemFanta = findViewById(R.id.imageButtonFanta);
        imagemSprite = findViewById(R.id.imageButtonSprite);
        imagemPepsi = findViewById(R.id.imageButtonPepsi);

        radioCoca = findViewById(R.id.radioButtonCoca);
        radioFanta = findViewById(R.id.radioButtonFanta);
        radioSprite = findViewById(R.id.radioButtonSprite);
        radioPepsi = findViewById(R.id.radioButtonPepsi);
         */

        //Aaaaaaa mannnnnnnn
        inputLayoutNomeDoce = findViewById(R.id.nome_doceInputLayout);
        inputLayoutValor = findViewById(R.id.valor_doceInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_doceInputLayout);

        Intent intent = getIntent();

        btnVoltar.setOnClickListener(view -> {
            Intent intentGoDoceListActivity = new Intent(getApplicationContext(), DoceListActivity.class);
            startActivity(intentGoDoceListActivity);
        });


        editTextNomeDoce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertNomeDoce = s.toString();
                if (alertNomeDoce.length() != 0) { //tem o matcher com o pattern pra verificar os intervalos no input, mas aqui não ficaria interessante
                    inputLayoutNomeDoce.setHelperText("");
                    inputLayoutNomeDoce.setError("");
                } else {
                    inputLayoutNomeDoce.setHelperText("");
                    inputLayoutNomeDoce.setError("Campo Obrigatório");
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
                    inputLayoutValor.setHelperText("");
                    inputLayoutValor.setError("");

                } else {
                    inputLayoutValor.setHelperText("");
                    inputLayoutValor.setError("Campo Obrigatório");
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
                    inputLayoutDescricao.setHelperText("");
                    inputLayoutDescricao.setError("");
                } else {
                    inputLayoutDescricao.setHelperText("");
                    inputLayoutDescricao.setError("Campo Obrigatório");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // A única forma de os dois serem iguais é se n houver erro nenhum. Pq desde o inicio da activity eles são diferentes
        Boolean compareInputNomeDoce = Objects.equals(inputLayoutNomeDoce.getHelperText(), inputLayoutNomeDoce.getError());
        Boolean compareInputValor = Objects.equals(inputLayoutValor.getHelperText(), inputLayoutValor.getError()); // a IDE que me ajudou a fazer essa construção de dados, eu ia deixar String.valueOf(obj).equals() msm
        Boolean compareInputDescricao = Objects.equals(inputLayoutDescricao.getHelperText(), inputLayoutDescricao.getError());


        //tentei fazer isso com o switch, mas ele n compara boolean
        if (compareInputNomeDoce && compareInputValor && compareInputDescricao) { //retorna por padrão true
            iniciandoComponentes();
        }

    }

    private void iniciandoComponentes() {

        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class); //ele vai gerar uma nova requisição http, e nesse caso do tipo POST

        btnBotao.setOnClickListener(v ->
        {
            String nomeDoce = editTextNomeDoce.getText().toString();
            String descricao = editTextDescricao.getText().toString();
            String strValor = editTextValor.getText().toString();
            //String imgDoce = editTextImagem.getText().toString(); // chamar da classe holder
            double valor = Double.parseDouble(strValor); // se ele ta dando erro vo botar ele aqui, só uso ele nesse objeto msm

            Doce doce = new Doce();
            doce.setNomeDoce(nomeDoce);
            doce.setValor(valor);
            doce.setDescricao(descricao);
            //doce.setImagem(imgDoce);

            doceAPI.addDoce(doce) //chama o método POST
                    .enqueue(new Callback<Doce>() { //deixa as requisições em fila
                        @Override
                        public void onResponse(Call<Doce> call, Response<Doce> response) {
                            Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                            Intent intentGoDoceListActivity = new Intent(getApplicationContext(), DoceListActivity.class);
                            startActivity(intentGoDoceListActivity);
                        }

                        @Override
                        public void onFailure(Call<Doce> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Não salvou!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(DoceActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                        }
                    });

        });
    }
}