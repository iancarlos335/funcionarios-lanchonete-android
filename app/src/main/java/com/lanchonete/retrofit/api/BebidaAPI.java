package com.lanchonete.retrofit.api;

import com.lanchonete.model.Bebida;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface BebidaAPI { //N funciona se for uma classe

    @GET("/bebidas")
    Call <List<Bebida>> listBebida();

    @POST("/bebidas")
    Call <Bebida> addBebida(@Body Bebida bebidaDto);


    @DELETE("/bebidas/{id}")
    Call <Bebida> delete(@Path("id") long id);

}
