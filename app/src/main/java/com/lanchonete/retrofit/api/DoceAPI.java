package com.lanchonete.retrofit.api;


import com.lanchonete.model.Doce;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DoceAPI {

    @GET("/doces")
    Call<List<Doce>> listDoce();

    @POST("/doces")
    Call <Doce> addDoce(@Body Doce doceDto);

    @DELETE("/doces/{id}")
    Call <Doce> delete(@Path("id") long id);
}
