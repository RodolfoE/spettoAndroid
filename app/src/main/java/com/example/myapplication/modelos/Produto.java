package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produto {
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("valor")
    @Expose
    private String valor;

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
