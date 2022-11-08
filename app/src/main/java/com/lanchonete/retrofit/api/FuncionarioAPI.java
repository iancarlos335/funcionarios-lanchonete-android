package com.lanchonete.retrofit.api;


import com.lanchonete.model.Funcionario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FuncionarioAPI {
    @GET("/funcionarios")
    Call<List<Funcionario>> listFuncionario();

    @POST("/funcionarios")
    Call <Funcionario> addFuncionario(@Body Funcionario funcionarioDto);
}
