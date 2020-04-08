package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categoria {

    public Categoria() {
        this.id_categoria = id_categoria;
        this.nome = nome;
    }
    public Categoria(int id_categoria, String nome) {
        this.id_categoria = id_categoria;
        this.nome = nome;
    }

    @SerializedName("id_categoria")
    @Expose
    int id_categoria;

    @SerializedName("nome")
    @Expose
    String nome;

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
