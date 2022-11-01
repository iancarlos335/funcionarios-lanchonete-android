package com.lanchonete.retrofit.api;

import com.lanchonete.model.Bebida;
import com.lanchonete.model.Salgado;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SalgadoAPI { //N funciona se for uma classe

    @GET("/salgados")
    Call <List<Salgado>> listSalgado();

    @POST("/salgados")
    Call <Salgado> addSalgado(@Body Salgado salgadoDto);

}
