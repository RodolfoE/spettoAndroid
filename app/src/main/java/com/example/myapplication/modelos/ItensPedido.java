package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItensPedido {
    public ItensPedido(Produto[] itensPedido, String tipoDono, int donoPedido, int idPedidos, int idResponsavel) {
        ItensPedido = itensPedido;
        this.tipoDono = tipoDono;
        this.donoPedido = donoPedido;
        this.idPedidos = idPedidos;
        this.idResponsavel = idResponsavel;
    }

    @SerializedName("id_responsavel")
    @Expose
    private int idResponsavel;

    @SerializedName("itens_pedido")
    @Expose
    private Produto[] ItensPedido;

    @SerializedName("tipo_dono")
    @Expose
    private String tipoDono;

    @SerializedName("dono_pedido")
    @Expose
    private int donoPedido;

    @SerializedName("id_pedido")
    @Expose
    private int idPedidos;

    public Produto[] getItensPedido() {
        return ItensPedido;
    }

    public void setItensPedido(Produto[] itensPedido) {
        ItensPedido = itensPedido;
    }

    public String getTipoDono() {
        return tipoDono;
    }

    public void setTipoDono(String tipoDono) {
        this.tipoDono = tipoDono;
    }

    public int getDonoPedido() {
        return donoPedido;
    }

    public void setDonoPedido(int donoPedido) {
        this.donoPedido = donoPedido;
    }

    public int getIdPedidos() {
        return idPedidos;
    }

    public void setIdPedidos(int idPedidos) {
        this.idPedidos = idPedidos;
    }




}
