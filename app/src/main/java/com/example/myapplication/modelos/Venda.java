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

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getQt_pago() {
        return qt_pago;
    }

    public void setQt_pago(double qt_pago) {
        this.qt_pago = qt_pago;
    }

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getId_forma() {
        return id_forma;
    }

    public void setId_forma(int id_forma) {
        this.id_forma = id_forma;
    }

    public String getSugestao() {
        return sugestao;
    }

    public void setSugestao(String sugestao) {
        this.sugestao = sugestao;
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
