package com.example.myapplication.servico;

import android.util.Log;

import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.modelos.ItensPedidoFeito;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Chamadas {

    public static void obterMesas(int idMesa, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call;

        call = (idMesa != -1) ? produtos.getMesas(idMesa) : produtos.getMesas();
        call.enqueue(callback);
    }

    public static void obterClientes(int idCliente, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call;

        call = (idCliente != -1) ? produtos.getClientes(idCliente) : produtos.getClientes();
        call.enqueue(callback);
    }

    public static void obterClientesDelivery(int idClienteDel, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call;

        call = (idClienteDel != -1) ? produtos.getClientesDelivery(idClienteDel) : produtos.getClientesDelivery();
        call.enqueue(callback);
    }
}
