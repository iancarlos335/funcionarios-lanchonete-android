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

    TextInputLayout inputLayoutNome, inputLayoutValor, inputLayoutDescricao;
    EditText editTextNome, editTextValor, editTextDescricao;

    ImageButton btnVoltar, btnPudim, btnBolo, btnDonuts;
    RadioButton radioPudim, radioBolo, radioDonuts, radioButtonFinal;
    RadioGroup radioGroup;

    Button btnCadastrar;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doce_activity);

        btnVoltar = findViewById(R.id.btnDoceVoltar);
        btnCadastrar = findViewById(R.id.botao_doce);
        editTextNome = findViewById(R.id.nome_doce);
        editTextValor = findViewById(R.id.valor_doce);
        editTextDescricao = findViewById(R.id.descricao_doce);

        btnPudim = findViewById(R.id.imageButtonPudim);
        btnBolo = findViewById(R.id.imageButtonBolo);
        btnDonuts = findViewById(R.id.imageButtonDonuts);

        radioPudim = findViewById(R.id.radioButtonPudim);
        radioBolo = findViewById(R.id.radioButtonBolo);
        radioDonuts = findViewById(R.id.radioButtonDonuts);

        radioGroup = findViewById(R.id.radioDoce);

        //Aaaaaaa mannnnnnnn
        inputLayoutNome = findViewById(R.id.nome_doceInputLayout);
        inputLayoutValor = findViewById(R.id.valor_doceInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_doceInputLayout);

        Intent intent = getIntent();

        btnVoltar.setOnClickListener(view -> {
            finish();
        });


        editTextNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertNomeDoce = s.toString();
                if (alertNomeDoce.length() != 0) { //pode ser feita a validação de intervalos regex
                    inputLayoutNome.setHelperText("");
                    inputLayoutNome.setError("");
                } else {
                    inputLayoutNome.setHelperText("");
                    inputLayoutNome.setError("Campo Obrigatório");
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

        btnPudim.setOnClickListener(v -> radioPudim.setChecked(true));
        btnBolo.setOnClickListener(v -> radioBolo.setChecked(true));
        btnDonuts.setOnClickListener(v -> radioDonuts.setChecked(true));

        btnCadastrar.setOnClickListener(view -> {
            int radioId = radioGroup.getCheckedRadioButtonId();

            boolean nomeEmpty = editTextNome.toString().isEmpty();
            boolean valorEmpty = editTextValor.toString().isEmpty();
            boolean descricaoEmpty = editTextDescricao.toString().isEmpty();

            if (nomeEmpty) {
                inputLayoutNome.requestFocus();
            } else if (valorEmpty) {
                inputLayoutValor.requestFocus();
            } else if (descricaoEmpty) {
                inputLayoutDescricao.requestFocus();
            }

            if (nomeEmpty && valorEmpty && descricaoEmpty && (radioId != -1)) {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                onRestart();
            } else {
                iniciandoComponentes();
            }
        });
    }

    private void iniciandoComponentes() {

        RetrofitService retrofitService = new RetrofitService();
        DoceAPI doceAPI = retrofitService.getRetrofit().create(DoceAPI.class);

        String nomeDoce = editTextNome.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String strValor = editTextValor.getText().toString();
        String imgDoce = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(); // esse q é o casting loko que o Jonas encontrou na internet
        double valor = Double.parseDouble(strValor);

        Doce doce = new Doce();
        doce.setNomeDoce(nomeDoce);
        doce.setValor(valor);
        doce.setDescricao(descricao);
        doce.setImagem(imgDoce);

        // Creating
        doceAPI.addDoce(doce)
                .enqueue(new Callback<Doce>() {
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


