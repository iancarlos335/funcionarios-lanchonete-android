package com.lanchonete.funcionario.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.lanchonete.R;
import com.lanchonete.model.Salgado;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.SalgadoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SalgadoActivity extends AppCompatActivity {

    TextInputLayout inputLayoutNome, inputLayoutValor, inputLayoutDescricao;
    EditText editTextNome, editTextValor, editTextDescricao;

    ImageButton btnVoltar, btnHamburguer, btnTriploHamburguer, btnXSaladaEgg;
    RadioButton radioHamburguer, radioTriploHamburguer, radioXSaladaEgg;
    RadioGroup radioGroup;

    Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salgado_activity);

        btnVoltar = findViewById(R.id.btnSalgadoVoltar);
        btnCadastrar = findViewById(R.id.botao_Salgado);
        editTextNome = findViewById(R.id.nome_salgado);
        editTextValor = findViewById(R.id.valor_salgado);
        editTextDescricao = findViewById(R.id.descricao_salgado);

        btnHamburguer = findViewById(R.id.imageButtonHamburguer);
        btnTriploHamburguer = findViewById(R.id.imageButtonTriploHamburguer);
        btnXSaladaEgg = findViewById(R.id.imageButtonXSaladaEgg);

        radioHamburguer = findViewById(R.id.radioButtonHamburguer);
        radioTriploHamburguer = findViewById(R.id.radioButtonTriploHamburguer);
        radioXSaladaEgg = findViewById(R.id.radioButtonXSaladaEgg);

        radioGroup = findViewById(R.id.radioSalgado);

        inputLayoutNome = findViewById(R.id.nome_salgadoInputLayout);
        inputLayoutValor = findViewById(R.id.valor_salgadoInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_salgadoInputLayout);

        btnVoltar.setOnClickListener(view -> finish());


        editTextNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String alertNomeSalgado = s.toString();
                if (alertNomeSalgado.length() != 0) { //pode ser feita a validação de intervalos regex
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

        btnHamburguer.setOnClickListener(v -> radioHamburguer.setChecked(true));
        btnTriploHamburguer.setOnClickListener(v -> radioTriploHamburguer.setChecked(true));
        btnXSaladaEgg.setOnClickListener(v -> radioXSaladaEgg.setChecked(true));

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
                cadastrar();
            }
        });
    }


    private void cadastrar() {
        RetrofitService retrofitService = new RetrofitService();
        SalgadoAPI salgadoAPI = retrofitService.getRetrofit().create(SalgadoAPI.class);


        String nomeSalgado = editTextNome.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String strValor = editTextValor.getText().toString();
        String imgSalgado = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(); // esse q é o casting loko que o Jonas encontrou na internet
        double valor = Double.parseDouble(strValor);

        Salgado salgado = new Salgado();
        salgado.setNome(nomeSalgado);
        salgado.setValor(valor);
        salgado.setDescricao(descricao);
        salgado.setImagem(imgSalgado);

        salgadoAPI.addSalgado(salgado)
                .enqueue(new Callback<Salgado>() {
                    @Override
                    public void onResponse(Call<Salgado> call, Response<Salgado> response) {
                        Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();

                        Intent returnIntent = new Intent();
                        String[] salgadoArray = new String[6];

                        salgado.setSelected(false);

                        salgadoArray[0] = String.valueOf(salgado.getId());
                        salgadoArray[1] = salgado.getNome();
                        salgadoArray[2] = String.valueOf(salgado.getValor());
                        salgadoArray[3] = salgado.getDescricao();
                        salgadoArray[4] = salgado.getImagem();
                        salgadoArray[5] = String.valueOf(salgado.isSelected());

                        returnIntent.putExtra("salgadoArray", salgadoArray);
                        setResult(RESULT_OK, returnIntent); //se o resultado não for OK, os dados não serão recebidos
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Salgado> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Não salvou!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(SalgadoActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                    }
                });
    }

}


