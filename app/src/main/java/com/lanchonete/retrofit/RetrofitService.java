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
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.48:8080/")
                .addConverterFactory(GsonConverterFactory.create(new Gson())) //can be another architecture to send data
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
