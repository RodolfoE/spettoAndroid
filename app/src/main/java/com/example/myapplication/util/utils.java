package com.example.myapplication.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.collection.ArrayMap;
import androidx.core.util.Pair;

import com.example.myapplication.EscolherProdutoActivity;
import com.example.myapplication.modelos.ItensPedidoFeito;
import com.example.myapplication.modelos.Produto;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

public class utils {
    public static int qtdAparicoesArray(ItensPedidoFeito[] array, String id){
        int qtd = 0;
        for (ItensPedidoFeito i: array) {
            if (i.getIdProduto().equals(id)){
                qtd++;
            }
        }
        return qtd;
    }

    public static int qtdAparicoesArray(ArrayList<Produto> array, String id){
        int qtd = 0;
        for (Produto i: array) {
            if (i.getIdProduto().equals(id)){
                qtd++;
            }
        }
        return qtd;
    }

    public static Produto[] ordernarArray(Produto[] array){
        ArrayList<Produto> novoArray = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            Produto prod = getMaior(array);
            if (prod != null)
                novoArray.add(prod);
        }
        return novoArray.toArray(new Produto[novoArray.size()]);
    }

    public static Produto getMaior(Produto[] array){
        Produto maior = new Produto("bla");
        maior.setQtd(-1);
        int indx = -1;
        for (int i = 0; i < array.length; i++) {
            Produto el = array[i];
            if (el != null && (el.getQtd() > maior.getQtd()) ){
                maior = el;
                indx = i;
            }
        }
        if (indx == -1)
            return null;
        array[indx] = null;
        return maior;
    }

    public static void tratamentoDeErroPadrao(Exception e, View ctx){
        Snackbar mySnackbar = Snackbar.make(ctx, "Ocorreu um erro.", 3000);
        mySnackbar.show();
        Log.e("Erro", e.getMessage());
    }

    public static ProgressDialog showLoading(Context ctx, String msg){
        final ProgressDialog progress = new ProgressDialog(ctx);
        progress.setTitle("Carregando");
        progress.setMessage(msg);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        return progress;
    }

    public static void abrirActivity(Context ctx, Class tClass, ArrayMap<String, Object> extras){
        Intent intent = new Intent(ctx, tClass);
        if (extras != null){
            for (Map.Entry<String, Object> entry : extras.entrySet()) {
                if (entry.getValue() instanceof String){
                    intent.putExtra(entry.getKey(), entry.getValue().toString());
                }
                if (entry.getValue() instanceof Integer){
                    intent.putExtra(entry.getKey(), ((Integer) entry.getValue()));
                }

                if (entry.getValue() instanceof Boolean){
                    intent.putExtra(entry.getKey(), ((Boolean) entry.getValue()));
                }
            }
        }
        ctx.startActivity(intent);
    }


}
