package com.example.myapplication.HttpRequests;

import com.example.myapplication.modelos.Categoria;
import com.example.myapplication.modelos.Cliente;
import com.example.myapplication.modelos.ClienteDelivery;
import com.example.myapplication.modelos.FormaPagamento;
import com.example.myapplication.modelos.ItensPedido;
import com.example.myapplication.modelos.ItensPedidoFeito;
import com.example.myapplication.modelos.Mesa;
import com.example.myapplication.modelos.Praca;
import com.example.myapplication.modelos.Produto;
import com.example.myapplication.modelos.Venda;

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

    @GET("/pedidos/obter_itens_pedido")
    Call<ItensPedidoFeito[]> getItensPedido(@Query("id_pedido") int idPedido);

    @POST("/pedidos/post_pedido_itens")
    Call<ItensPedido> postProduto(@Body ItensPedido itens_pedido);

    @GET("/pedidos/obter_mesas")
    Call<Mesa> getMesas(@Body int id_dono);

    @GET("/pedidos/obter_mesas")
    Call<Mesa[]> getMesas();

    @GET("/pedidos/obter_clientes")
    Call<Cliente[]> getClientes();

    @GET("/pedidos/obter_clientes")
    Call<Cliente[]> getClientes(@Body int id_cliente);

    @GET("/pedidos/obter_clientes_delivery")
    Call<ClienteDelivery[]> getClientesDelivery(@Body int id_cliente);

    @GET("/pedidos/obter_clientes_delivery")
    Call<ClienteDelivery[]> getClientesDelivery();

    @POST("/pedidos/cadastrar_cliente")
    Call<Cliente> postCliente(@Body Cliente cliente);

    @POST("/pedidos/cadastrar_mesa")
    Call<Mesa> postMesa(@Body Mesa mesa);

    @POST("/pedidos/fechar_pedido")
    Call<Void> fecharPedido(@Body Venda venda);

    @GET("/pedidos/obter_formas_pagamento")
    Call<FormaPagamento[]> obterFormasPagamento();

    @GET("/pedidos/obter_parciais_pedido")
    Call<Venda[]> obterParciaisPedido(@Query("itensSelect") String[] itensSelect,@Query("id_pedido") int id_pedido);

    @GET("/praca/get_pracas")
    Call<Praca[]> obterPracas();

    @GET("/produto/get_categoria")
    Call<Categoria[]> obterCategorias();

    @POST("/produto/post_produto")
    Call<Void> cadastrarProduto(@Body Produto produto);

    @POST("/praca/post_praca")
    Call<Void> cadastrarPraca(@Body Praca praca);

    @POST("/produto/post_categoria")
    Call<Void> cadastrarCategoria(@Body Categoria categoria);

}
