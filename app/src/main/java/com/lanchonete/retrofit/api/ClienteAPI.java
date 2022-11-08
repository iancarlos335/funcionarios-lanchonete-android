package com.lanchonete.retrofit.api;


import com.lanchonete.model.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClienteAPI {

    @GET("/clientes")
    Call<List<Cliente>> listCliente();

    @POST("/clientes")
    Call <Cliente> addCliente(@Body Cliente clienteDto);
}
