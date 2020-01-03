package com.example.myapplication.modelos;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ItensPedidoFeito {
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    @SerializedName("id_pedido")
    @Expose
    private int idPedido;

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    @SerializedName("id_produto")
    @Expose
    private String idProduto;

    @SerializedName("ordem")
    @Expose
    private int ordem;


}
