package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Praca {

    @SerializedName("id_responsavel")
    @Expose
    int id_responsavel;

    @SerializedName("id_praca")
    @Expose
    int id_praca;

    @SerializedName("nome")
    @Expose
    String nome;

    public int getId_responsavel() {
        return id_responsavel;
    }

    public void setId_responsavel(int id_responsavel) {
        this.id_responsavel = id_responsavel;
    }

    public int getId_praca() {
        return id_praca;
    }

    public void setId_praca(int id_praca) {
        this.id_praca = id_praca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
