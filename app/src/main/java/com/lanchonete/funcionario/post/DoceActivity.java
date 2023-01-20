package com.lanchonete.funcionario.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.lanchonete.R;

import com.lanchonete.funcionario.MenuFuncionario;
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

    ImageButton btnVoltar, imagemPudim, imagemBolo, imagemDonuts;
    RadioButton radioPudim, radioBolo, radioDonuts, radioButtonFinal;
    RadioGroup radioGroup;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doce_activity);

        btnVoltar = findViewById(R.id.btnDoceVoltar);
        btnBotao = findViewById(R.id.botao_doce);
        editTextNomeDoce = findViewById(R.id.nome_doce);
        editTextValor = findViewById(R.id.valor_doce);
        editTextDescricao = findViewById(R.id.descricao_doce); //adicionar input direito

        imagemPudim = findViewById(R.id.imageButtonPudim);
        imagemBolo = findViewById(R.id.imageButtonBolo);
        imagemDonuts = findViewById(R.id.imageButtonDonuts);

        radioPudim = findViewById(R.id.radioButtonPudim);
        radioBolo = findViewById(R.id.radioButtonBolo);
        radioDonuts = findViewById(R.id.radioButtonDonuts);

        radioGroup = findViewById(R.id.radioDoce);

        //Aaaaaaa mannnnnnnn
        inputLayoutNomeDoce = findViewById(R.id.nome_doceInputLayout);
        inputLayoutValor = findViewById(R.id.valor_doceInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_doceInputLayout);

        Intent intent = getIntent();

        btnVoltar.setOnClickListener(view -> {
            finish();
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
        boolean compareInputNomeDoce = Objects.equals(inputLayoutNomeDoce.getHelperText(), inputLayoutNomeDoce.getError());
        boolean compareInputValor = Objects.equals(inputLayoutValor.getHelperText(), inputLayoutValor.getError()); // a IDE que me ajudou a fazer essa construção de dados, eu ia deixar String.valueOf(obj).equals() msm
        boolean compareInputDescricao = Objects.equals(inputLayoutDescricao.getHelperText(), inputLayoutDescricao.getError());

        imagemPudim.setOnClickListener(v -> radioPudim.setChecked(true)); //ativa radio pela imagem
        imagemBolo.setOnClickListener(v -> radioBolo.setChecked(true));
        imagemDonuts.setOnClickListener(v -> radioDonuts.setChecked(true));

        btnBotao.setOnClickListener(view -> {
            if (compareInputNomeDoce && compareInputValor && compareInputDescricao) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButtonFinal = findViewById(radioId);
                iniciandoComponentes();
            }
        });
    }

    public void checkButtonDoce(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButtonFinal = findViewById(radioId);
    }


    private void iniciandoComponentes() {

        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class); //ele vai gerar uma nova requisição http, e nesse caso do tipo POST

        String nomeDoce = editTextNomeDoce.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String strValor = editTextValor.getText().toString();
        String imgDoce = radioButtonFinal.getText().toString();// tentei com o to string e n deu certo
        double valor = Double.parseDouble(strValor); // se ele ta dando erro vo botar ele aqui, só uso ele nesse objeto msm

        //ele só vai receber alguma coisa se tiver algum ID rodando

        Doce doce = new Doce();
        doce.setNomeDoce(nomeDoce);
        doce.setValor(valor);
        doce.setDescricao(descricao);
        doce.setImagem(imgDoce);

        doceAPI.addDoce(doce) //chama o método POST
                .enqueue(new Callback<Doce>() { //deixa as requisições em fila
                    @Override
                    public void onResponse(Call<Doce> call, Response<Doce> response) {
                        Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Doce> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Não salvou!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(DoceActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                    }
                });

    }
}


