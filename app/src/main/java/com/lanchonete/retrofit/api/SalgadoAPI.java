package com.lanchonete.retrofit.api;

import com.lanchonete.model.Doce;
import com.lanchonete.model.Salgado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface SalgadoAPI {
    @GET("/salgados")
    Call<List<Salgado>> listSalgado();

    @POST("/salgados")
    Call <Salgado> addSalgado(@Body Salgado salgadoDto);

    @DELETE("/salgados/{id}")
    Call <Salgado> delete(@Path("id") long id);
}
