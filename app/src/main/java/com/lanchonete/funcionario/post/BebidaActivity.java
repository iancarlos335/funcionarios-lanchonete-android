package com.lanchonete.funcionario.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.lanchonete.R;
import com.lanchonete.funcionario.get.bebida.BebidaAdapter;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BebidaActivity extends AppCompatActivity {

    TextInputLayout inputLayoutNome, inputLayoutValor, inputLayoutDescricao;
    EditText editTextNome, editTextValor, editTextDescricao;

    ImageButton btnVoltar, btnCoca, btnFanta, btnGuarana, btnPepsi;
    RadioButton radioCoca, radioFanta, radioGuarana, radioPepsi;
    RadioGroup radioGroup;

    Button btnCadastrar;

    private final Bebida bebida = new Bebida();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebida_activity);

        btnVoltar = findViewById(R.id.btnBebidaVoltar);
        btnCadastrar = findViewById(R.id.botao_bebida);
        editTextNome = findViewById(R.id.nome_bebida);
        editTextValor = findViewById(R.id.valor_bebida);
        editTextDescricao = findViewById(R.id.descricao_bebida);

        btnCoca = findViewById(R.id.imageButtonCoca);
        btnFanta = findViewById(R.id.imageButtonFanta);
        btnGuarana = findViewById(R.id.imageButtonGuarana);
        btnPepsi = findViewById(R.id.imageButtonPepsi);

        radioCoca = findViewById(R.id.radioButtonCoca);
        radioFanta = findViewById(R.id.radioButtonFanta);
        radioGuarana = findViewById(R.id.radioButtonGuarana);
        radioPepsi = findViewById(R.id.radioButtonPepsi);

        radioGroup = findViewById(R.id.radioBebida);

        //Aaaaaaa mannnnnnnn
        inputLayoutNome = findViewById(R.id.nome_bebidaInputLayout);
        inputLayoutValor = findViewById(R.id.valor_bebidaInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_bebidaInputLayout);

        btnVoltar.setOnClickListener(view -> finish());


        editTextNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String alertNomeBebida = s.toString();
                if (alertNomeBebida.length() != 0) { //pode ser feita a validação de intervalos regex
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

        btnCoca.setOnClickListener(v -> radioCoca.setChecked(true));
        btnFanta.setOnClickListener(v -> radioFanta.setChecked(true));
        btnGuarana.setOnClickListener(v -> radioGuarana.setChecked(true));
        btnPepsi.setOnClickListener(v -> radioPepsi.setChecked(true));

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
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class);

        String nomeBebida = editTextNome.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String strValor = editTextValor.getText().toString();
        String imgBebida = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(); // esse q é o casting loko que o Jonas encontrou na internet
        double valor = Double.parseDouble(strValor);


        bebida.setNomeBebida(nomeBebida);
        bebida.setValor(valor);
        bebida.setDescricao(descricao);
        bebida.setImagem(imgBebida);

        bebidaAPI.addBebida(bebida)
                .enqueue(new Callback<Bebida>() {
                    @Override
                    public void onResponse(Call<Bebida> call, Response<Bebida> response) {
                        Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Bebida> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Não salvou!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(BebidaActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                    }
                });
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        String[] bebidaArray = new String[6];

        bebida.setSelected(false);

        bebidaArray[0] = String.valueOf(bebida.getId());
        bebidaArray[1] = bebida.getNomeBebida();
        bebidaArray[2] = String.valueOf(bebida.getValor());
        bebidaArray[3] = bebida.getDescricao();
        bebidaArray[4] = bebida.getImagem();
        bebidaArray[5] = String.valueOf(bebida.isSelected());

        returnIntent.putExtra("bebidaArray", bebidaArray);
        setResult(RESULT_OK, returnIntent); //se o resultado não for OK, o dado que for retornado no parâmetro vai estar vazio.
        super.finish();
    }
}