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


import com.lanchonete.model.Salgado;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.SalgadoAPI;


import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalgadoActivity extends AppCompatActivity {

    TextInputLayout inputLayoutNomeSalgado, inputLayoutValor, inputLayoutDescricao;
    EditText editTextNomeSalgado, editTextValor, editTextDescricao;
    Button btnBotao;

    ImageButton btnVoltar, imagemCoca, imagemFanta, imagemSprite, imagemPepsi;
    RadioButton radioCoca, radioFanta, radioSprite, radioPepsi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salgado_activity);

        btnVoltar = findViewById(R.id.btnSalgadoVoltar);
        btnBotao = findViewById(R.id.botao_salgado);
        editTextNomeSalgado = findViewById(R.id.nome_salgado);
        editTextValor = findViewById(R.id.valor_salgado);
        editTextDescricao = findViewById(R.id.descricao_salgado); //adicionar input direito

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
        inputLayoutNomeSalgado = findViewById(R.id.nome_salgadoInputLayout);
        inputLayoutValor = findViewById(R.id.valor_salgadoInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_salgadoInputLayout);

        Intent intent = getIntent();

        btnVoltar.setOnClickListener(view -> {
            Intent intentGoSalgadoListActivity = new Intent(getApplicationContext(), SalgadoActivity.class);
            startActivity(intentGoSalgadoListActivity);
        });


        editTextNomeSalgado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertNomeSalgado = s.toString();
                if (alertNomeSalgado.length() != 0) { //tem o matcher com o pattern pra verificar os intervalos no input, mas aqui não ficaria interessante
                    inputLayoutNomeSalgado.setHelperText("");
                    inputLayoutNomeSalgado.setError("");
                } else {
                    inputLayoutNomeSalgado.setHelperText("");
                    inputLayoutNomeSalgado.setError("Campo Obrigatório");
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
        Boolean compareInputNomeSalgado = Objects.equals(inputLayoutNomeSalgado.getHelperText(), inputLayoutNomeSalgado.getError());
        Boolean compareInputValor = Objects.equals(inputLayoutValor.getHelperText(), inputLayoutValor.getError()); // a IDE que me ajudou a fazer essa construção de dados, eu ia deixar String.valueOf(obj).equals() msm
        Boolean compareInputDescricao = Objects.equals(inputLayoutDescricao.getHelperText(), inputLayoutDescricao.getError());


        //tentei fazer isso com o switch, mas ele n compara boolean
        if (compareInputNomeSalgado && compareInputValor && compareInputDescricao) { //retorna por padrão true
            iniciandoComponentes();
        }

    }

    private void iniciandoComponentes() {

        RetrofitService retrofitService = new RetrofitService();
        SalgadoAPI salgadoAPI = retrofitService.getRetrofit().create(SalgadoAPI.class); //ele vai gerar uma nova requisição http, e nesse caso do tipo POST

        btnBotao.setOnClickListener(v ->
        {
            String nomeSalgado = editTextNomeSalgado.getText().toString();
            String descricao = editTextDescricao.getText().toString();
            String strValor = editTextValor.getText().toString();
            //String imgSalgado = editTextImagem.getText().toString(); // chamar da classe holder
            double valor = Double.parseDouble(strValor); // se ele ta dando erro vo botar ele aqui, só uso ele nesse objeto msm

            Salgado salgado = new Salgado();
            salgado.setNomeSalgado(nomeSalgado);
            salgado.setValor(valor);
            salgado.setDescricao(descricao);
            //salgado.setImagem(imgSalgado);

            salgadoAPI.addSalgado(salgado) //chama o método POST
                    .enqueue(new Callback<Salgado>() { //deixa as requisições em fila
                        @Override
                        public void onResponse(Call<Salgado> call, Response<Salgado> response) {
                            Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                            Intent intentGoSalgadoListActivity = new Intent(getApplicationContext(), SalgadoActivity.class);
                            startActivity(intentGoSalgadoListActivity);
                        }

                        @Override
                        public void onFailure(Call<Salgado> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Não salvou!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(SalgadoActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                        }
                    });

        });
    }
}