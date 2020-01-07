package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClienteDelivery {
    @SerializedName("id_dono")
    @Expose
    private int idDono;

    @SerializedName("id_cliente")
    @Expose
    private int idCLiente;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("id_contato")
    @Expose
    private int idContato;

    @SerializedName("id_endereco")
    @Expose
    private int idEndereco;

    @SerializedName("id_responsavel")
    @Expose
    private int idResponsavel;

    @SerializedName("id_entregador")
    @Expose
    private int idEntregador;


    @SerializedName("em_uso")
    @Expose
    private int emUso;


    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    @SerializedName("id_pedido")
    @Expose
    private int idPedido;

    public int getIdDono() {
        return idDono;
    }

    public void setIdDono(int idDono) {
        this.idDono = idDono;
    }

    public int getIdCLiente() {
        return idCLiente;
    }

    public void setIdCLiente(int idCLiente) {
        this.idCLiente = idCLiente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public int getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(int idEntregador) {
        this.idEntregador = idEntregador;
    }

    public int getEmUso() {
        return emUso;
    }

    public void setEmUso(int emUso) {
        this.emUso = emUso;
    }
}
