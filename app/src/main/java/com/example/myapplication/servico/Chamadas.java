package com.example.myapplication.servico;

import android.os.Build;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.modelos.Cliente;
import com.example.myapplication.modelos.ItensPedidoFeito;
import com.example.myapplication.modelos.Mesa;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

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

    public static void postCliente(Cliente cliente, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call = produtos.postCliente(cliente);
        call.enqueue(callback);
    }

    public static void postMesa(Mesa mesa, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call = produtos.postMesa(mesa);
        call.enqueue(callback);
    }

    public static void getProdutos(ArrayList<String[]> filtro, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        String filtroString = "";
        if (filtro != null){
            filtroString = "{";
            for (String[] i : filtro) {
                filtroString += "\"" + i[0] + "\": \"" + i[1] + "\"";
            }
            filtroString += "}";
        }
        call = produtos.getProdutos(filtroString);
        call.enqueue(callback);
    }

    public static void getCategorias(Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI categoria = retrofit.create(EscolherProdutosAPI.class);
        Call call = categoria.obterCategorias();
        call.enqueue(callback);
    }
}
