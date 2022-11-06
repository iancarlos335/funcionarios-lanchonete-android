package com.lanchonete.retrofit.api;

import com.lanchonete.model.Clientes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClientesAPI {

    @GET("/cliente") //é um saco esse negocio despadronizado kkkk
    Call<List<Clientes>> listClientes();

    @POST("/cliente")
    Call <Clientes> addClientes(@Body Clientes clientesDto);
}
