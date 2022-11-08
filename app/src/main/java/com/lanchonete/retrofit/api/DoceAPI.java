package com.lanchonete.retrofit.api;


import com.lanchonete.model.Doce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DoceAPI {

    @GET("/doces")
    Call<List<Doce>> listDoce();

    @POST("/doces")
    Call <Doce> addDoce(@Body Doce doceDto);
}
