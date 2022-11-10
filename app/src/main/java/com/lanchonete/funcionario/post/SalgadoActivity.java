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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.lanchonete.R;


import com.lanchonete.funcionario.get.salgado.SalgadoListActivity;
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

    ImageButton btnVoltar, imagemHamburguer, imagemTriploHamburguer, imagemXSaladaEgg;
    RadioButton radioHamburguer, radioTriploHamburguer, radioXSaladaEgg, radioButtonFinal;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salgado_activity);

        btnVoltar = findViewById(R.id.btnSalgadoVoltar);
        btnBotao = findViewById(R.id.botao_Salgado);
        editTextNomeSalgado = findViewById(R.id.nome_salgado);
        editTextValor = findViewById(R.id.valor_salgado);
        editTextDescricao = findViewById(R.id.descricao_salgado); //adicionar input direito

        imagemHamburguer = findViewById(R.id.imageButtonHamburguer);
        imagemTriploHamburguer = findViewById(R.id.imageButtonTriploHamburguer);
        imagemXSaladaEgg = findViewById(R.id.imageButtonXSaladaEgg);

        radioHamburguer = findViewById(R.id.radioButtonHamburguer);
        radioTriploHamburguer = findViewById(R.id.radioButtonTriploHamburguer);
        radioXSaladaEgg = findViewById(R.id.radioButtonXSaladaEgg);

        radioGroup = findViewById(R.id.radioSalgado);

        //Aaaaaaa mannnnnnnn
        inputLayoutNomeSalgado = findViewById(R.id.nome_salgadoInputLayout);
        inputLayoutValor = findViewById(R.id.valor_salgadoInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_salgadoInputLayout);

        Intent intent = getIntent();

        btnVoltar.setOnClickListener(view -> {
            Intent intentGoSalgadoListActivity = new Intent(getApplicationContext(), SalgadoListActivity.class);
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
        boolean compareInputNomeSalgado = Objects.equals(inputLayoutNomeSalgado.getHelperText(), inputLayoutNomeSalgado.getError());
        boolean compareInputValor = Objects.equals(inputLayoutValor.getHelperText(), inputLayoutValor.getError()); // a IDE que me ajudou a fazer essa construção de dados, eu ia deixar String.valueOf(obj).equals() msm
        boolean compareInputDescricao = Objects.equals(inputLayoutDescricao.getHelperText(), inputLayoutDescricao.getError());

        imagemHamburguer.setOnClickListener(v -> radioHamburguer.setChecked(true)); //ativa radio pela imagem
        imagemTriploHamburguer.setOnClickListener(v -> radioTriploHamburguer.setChecked(true));
        imagemXSaladaEgg.setOnClickListener(v -> radioXSaladaEgg.setChecked(true));

        btnBotao.setOnClickListener(view -> {
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButtonFinal = findViewById(radioId);
            iniciandoComponentes();
        });

        //tentei o empty tbm mas n dava certo nunca

        //tentei fazer isso com o switch, mas ele n compara boolean
        if (compareInputNomeSalgado && compareInputValor && compareInputDescricao) { //retorna por padrão true
            iniciandoComponentes();
        }

    }

    public void checkButtonSalgado(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButtonFinal = findViewById(radioId);
    }


    private void iniciandoComponentes() {

        RetrofitService retrofitService = new RetrofitService();
        SalgadoAPI salgadoAPI = retrofitService.getRetrofit().create(SalgadoAPI.class); //ele vai gerar uma nova requisição http, e nesse caso do tipo POST

        btnBotao.setOnClickListener(v ->
        {
            String nomeSalgado = editTextNomeSalgado.getText().toString();
            String descricao = editTextDescricao.getText().toString();
            String strValor = editTextValor.getText().toString();
            String imgSalgado = radioButtonFinal.getText().toString();// tentei com o to string e n deu certo
            double valor = Double.parseDouble(strValor); // se ele ta dando erro vo botar ele aqui, só uso ele nesse objeto msm

            //ele só vai receber alguma coisa se tiver algum ID rodando

            Salgado salgado = new Salgado();
            salgado.setNomeSalgado(nomeSalgado);
            salgado.setValor(valor);
            salgado.setDescricao(descricao);
            salgado.setImagem(imgSalgado);

            salgadoAPI.addSalgado(salgado) //chama o método POST
                    .enqueue(new Callback<Salgado>() { //deixa as requisições em fila
                        @Override
                        public void onResponse(Call<Salgado> call, Response<Salgado> response) {
                            Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                            Intent intentGoSalgadosListActivity = new Intent(getApplicationContext(), SalgadoActivity.class);
                            startActivity(intentGoSalgadosListActivity);
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


