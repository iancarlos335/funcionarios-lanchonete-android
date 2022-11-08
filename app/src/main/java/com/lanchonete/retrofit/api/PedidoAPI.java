package com.lanchonete.retrofit.api;

import com.lanchonete.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PedidoAPI {
    @GET("/pedidos")
    Call<List<Pedido>> listPedido();

    @POST("/pedidos")
    Call <Pedido> addPedido(@Body Pedido pedidoDto);
}
