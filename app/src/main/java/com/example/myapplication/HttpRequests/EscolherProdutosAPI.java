package com.example.myapplication.HttpRequests;

import com.example.myapplication.modelos.ItensPedido;
import com.example.myapplication.modelos.Produto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EscolherProdutosAPI {
    @GET("/produto/get_produtos")
    Call<Produto[]> getProdutos(); //ex: @Query("q") String city, @Query("appid") String apiKey

    @GET("/produto/get_produtos")
    Call<Produto[]> getProdutos(@Query("where") String where);

    @POST("/pedidos/post_pedido_itens")
    Call<Object> postProduto(@Body ItensPedido itens_pedido);
}
