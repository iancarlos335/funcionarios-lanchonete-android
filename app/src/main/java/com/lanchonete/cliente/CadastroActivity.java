package com.lanchonete.cliente;
//O android Studio não adiciona activities em diretórios que não pertencem ao pacote raiz. Nesse caso é o com.lanchonete
//eu movi, mas se der erro eu vou ter q colocar eles de volta no lugar de origem
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lanchonete.R;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_activity);
    }

}