package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormaPagamento {
    @SerializedName("id_forma")
    @Expose
    private int id_forma;

    @SerializedName("nome")
    @Expose
    private String nome;

    public int getId_forma() {
        return id_forma;
    }

    public void setId_forma(int id_forma) {
        this.id_forma = id_forma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static String[] obterListaDeFormas(FormaPagamento[] item){
        String[] retorno = new String[item.length];
        for (int i = 0; i < item.length; i++) {
            retorno[i] = item[i].nome;
        }
        return retorno;
    }
}
