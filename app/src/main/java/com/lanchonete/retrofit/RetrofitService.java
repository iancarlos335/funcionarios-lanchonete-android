package com.lanchonete.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
        iniciarRetrofit();
    }

    public void iniciarRetrofit() {
        retrofit = new Retrofit.Builder() //podem ser adiocionadas mais configurações dps desse Retrofit
                .baseUrl("https://localhost:8080") //É na porta 8080 q o TomCat sobe, só vai funcionar as inserções se a api tiver rodando na porta 8080
                .addConverterFactory(GsonConverterFactory.create(new Gson())) //Se for necessário definir parâmetros pra esse Gson dps, é só mdificar o objeto(Gson)
                .build();
    }

    public Retrofit getRetrofit() {
        return  retrofit;
    }

}
