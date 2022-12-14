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
import com.lanchonete.funcionario.get.bebida.BebidaListActivity;
import com.lanchonete.model.Bebida;
import com.lanchonete.retrofit.RetrofitService;
import com.lanchonete.retrofit.api.BebidaAPI;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BebidaActivity extends AppCompatActivity {

    TextInputLayout inputLayoutNomeBebida, inputLayoutValor, inputLayoutDescricao;
    EditText editTextNomeBebida, editTextValor, editTextDescricao;
    Button btnBotao;

    ImageButton btnVoltar, imagemCoca, imagemFanta, imagemGuarana, imagemPepsi;
    RadioButton radioCoca, radioFanta, radioGuarana, radioPepsi, radioButtonFinal;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebida_activity);

        btnVoltar = findViewById(R.id.btnBebidaVoltar);
        btnBotao = findViewById(R.id.botao_bebida);
        editTextNomeBebida = findViewById(R.id.nome_bebida);
        editTextValor = findViewById(R.id.valor_bebida);
        editTextDescricao = findViewById(R.id.descricao_bebida); //adicionar input direito

        imagemCoca = findViewById(R.id.imageButtonCoca);
        imagemFanta = findViewById(R.id.imageButtonFanta);
        imagemGuarana = findViewById(R.id.imageButtonGuarana);
        imagemPepsi = findViewById(R.id.imageButtonPepsi);

        radioCoca = findViewById(R.id.radioButtonCoca);
        radioFanta = findViewById(R.id.radioButtonFanta);
        radioGuarana = findViewById(R.id.radioButtonGuarana);
        radioPepsi = findViewById(R.id.radioButtonPepsi);

        radioGroup = findViewById(R.id.radioBebida);

        //Aaaaaaa mannnnnnnn
        inputLayoutNomeBebida = findViewById(R.id.nome_bebidaInputLayout);
        inputLayoutValor = findViewById(R.id.valor_bebidaInputLayout);
        inputLayoutDescricao = findViewById(R.id.descricao_bebidaInputLayout);

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
                if (alertNomeBebida.length() != 0) { //tem o matcher com o pattern pra verificar os intervalos no input, mas aqui n??o ficaria interessante
                    inputLayoutNomeBebida.setHelperText("");
                    inputLayoutNomeBebida.setError("");
                } else {
                    inputLayoutNomeBebida.setHelperText("");
                    inputLayoutNomeBebida.setError("Campo Obrigat??rio");
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
                    inputLayoutValor.setError("Campo Obrigat??rio");
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
                    inputLayoutDescricao.setError("Campo Obrigat??rio");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // A ??nica forma de os dois serem iguais ?? se n houver erro nenhum. Pq desde o inicio da activity eles s??o diferentes
        boolean compareInputNomeBebida = Objects.equals(inputLayoutNomeBebida.getHelperText(), inputLayoutNomeBebida.getError());
        boolean compareInputValor = Objects.equals(inputLayoutValor.getHelperText(), inputLayoutValor.getError()); // a IDE que me ajudou a fazer essa constru????o de dados, eu ia deixar String.valueOf(obj).equals() msm
        boolean compareInputDescricao = Objects.equals(inputLayoutDescricao.getHelperText(), inputLayoutDescricao.getError());

        imagemCoca.setOnClickListener(v -> {
            radioCoca.setChecked(true);
        });
        imagemFanta.setOnClickListener(v -> radioFanta.setChecked(true));
        imagemGuarana.setOnClickListener(v -> {
            radioGuarana.setChecked(true);
        });
        imagemPepsi.setOnClickListener(v -> {
            radioPepsi.setChecked(true);
        });

        btnBotao.setOnClickListener(view -> {
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButtonFinal = findViewById(radioId);
            iniciandoComponentes();
        });

        //tentei o empty tbm mas n dava certo nunca

        //tentei fazer isso com o switch, mas ele n compara boolean
        if (compareInputNomeBebida && compareInputValor && compareInputDescricao) { //retorna por padr??o true
            iniciandoComponentes();
        }

    }

    public void checkButtonBebida(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButtonFinal = findViewById(radioId);
    }


    private void iniciandoComponentes() {

        RetrofitService retrofitService = new RetrofitService();
        BebidaAPI bebidaAPI = retrofitService.getRetrofit().create(BebidaAPI.class); //ele vai gerar uma nova requisi????o http, e nesse caso do tipo POST

        btnBotao.setOnClickListener(v ->
        {
            String nomeBebida = editTextNomeBebida.getText().toString();
            String descricao = editTextDescricao.getText().toString();
            String strValor = editTextValor.getText().toString();
            String imgBebida = radioButtonFinal.getText().toString();// tentei com o to string e n deu certo
            double valor = Double.parseDouble(strValor); // se ele ta dando erro vo botar ele aqui, s?? uso ele nesse objeto msm

            //ele s?? vai receber alguma coisa se tiver algum ID rodando

            Bebida bebida = new Bebida();
            bebida.setNomeBebida(nomeBebida);
            bebida.setValor(valor);
            bebida.setDescricao(descricao);
            bebida.setImagem(imgBebida);

            bebidaAPI.addBebida(bebida) //chama o m??todo POST
                    .enqueue(new Callback<Bebida>() { //deixa as requisi????es em fila
                        @Override
                        public void onResponse(Call<Bebida> call, Response<Bebida> response) {
                            Toast.makeText(getApplicationContext(), "Salvo com sucesso no banco", Toast.LENGTH_SHORT).show();
                            Intent intentGoBebidaListActivity = new Intent(getApplicationContext(), BebidaListActivity.class);
                            startActivity(intentGoBebidaListActivity);
                        }

                        @Override
                        public void onFailure(Call<Bebida> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "N??o salvou!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(BebidaActivity.class.getName()).log(Level.SEVERE, "Um erro ocorreu", t);
                        }
                    });

        });
    }

}


