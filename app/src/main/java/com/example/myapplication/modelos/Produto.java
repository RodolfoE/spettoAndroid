package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produto {
    public Produto(){

    }

    public int getIdPraca() {
        return idPraca;
    }

    public void setIdPraca(int idPraca) {
        this.idPraca = idPraca;
    }

    @SerializedName("id_praca")
    @Expose
    private int idPraca;

    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("valor")
    @Expose
    private String valor;
    @SerializedName("id_produto")
    @Expose
    private String idProduto;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @SerializedName("categoria")
    @Expose
    private String categoria;

    @SerializedName("valor_de_custo")
    @Expose
    private String valor_de_custo;

    public String getValor_de_custo() {
        return valor_de_custo;
    }

    public void setValor_de_custo(String valor_de_custo) {
        this.valor_de_custo = valor_de_custo;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public Produto(String nome){
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    private int qtd;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
