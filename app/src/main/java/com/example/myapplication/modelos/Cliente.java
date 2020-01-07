package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cliente {
    @SerializedName("em_uso")
    @Expose
    private int emUso;

    @SerializedName("id_dono")
    @Expose
    private int idDono;

    @SerializedName("id_cliente")
    @Expose
    private int idMesa;

    @SerializedName("nome")
    @Expose
    private String nome;

    public int getIdPedido() {
        return idPedido;
    }

    @SerializedName("telefone")
    @Expose
    private String telefone;


    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    @SerializedName("id_pedido")
    @Expose
    private int idPedido;

    public int getEmUso() {
        return emUso;
    }

    public void setEmUso(int emUso) {
        this.emUso = emUso;
    }

    public int getIdDono() {
        return idDono;
    }

    public void setIdDono(int idDono) {
        this.idDono = idDono;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
