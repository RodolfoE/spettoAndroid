package com.example.myapplication.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mesa {
    @SerializedName("em_uso")
    @Expose
    private int emUso;

    @SerializedName("id_dono")
    @Expose
    private int idDono;

    @SerializedName("id_mesa")
    @Expose
    private int idMesa;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getEmUso() {
        return emUso;
    }

    public void setEmUso(int emUso) {
        this.emUso = emUso;
    }

}
