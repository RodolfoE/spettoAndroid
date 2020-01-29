package com.example.myapplication.modelos;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Venda {
    @SerializedName("id_pedido")
    @Expose
    private int id_pedido;

    @SerializedName("total")
    @Expose
    private double total;

    @SerializedName("qt_pago")
    @Expose
    private double qt_pago;

    @SerializedName("fechado")
    @Expose
    private boolean fechado;

    public Venda(int id_pedido, double total, double qt_pago, boolean fechado, int id_forma) {
        this.id_pedido = id_pedido;
        this.total = total;
        this.qt_pago = qt_pago;
        this.fechado = fechado;
        this.id_forma = id_forma;
    }

    @SerializedName("nota")
    @Expose
    private int nota;

    @SerializedName("id_forma")
    @Expose
    private int id_forma;

    @SerializedName("sugestao")
    @Expose
    private String sugestao;
}
