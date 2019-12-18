package com.example.myapplication.HttpRequests;

import com.example.myapplication.modelos.Produto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EscolherProdutosAPI {
    @GET("/produto/get_produtos")
    Call<Produto> getProdutos(); //ex: @Query("q") String city, @Query("appid") String apiKey
}
