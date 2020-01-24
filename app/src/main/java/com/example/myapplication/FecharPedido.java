package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.modelos.FormaPagamento;
import com.example.myapplication.modelos.Mesa;
import com.example.myapplication.modelos.Venda;
import com.example.myapplication.util.utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FecharPedido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechar_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initViews();
        fecharParcial();
    }

    private void initViews(){
        obterFormasDePagamento();
    }

    private void fecharParcial(){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call = produtos.fecharPedido(new Venda());

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void obterFormasDePagamento(){
//        final ProgressDialog progressDialog = utils.showLoading(getApplication(), "Adicionando cliente");
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call = produtos.obterFormasPagamento();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                FormaPagamento[] formas;
                if (response.body() != null){
                    formas = (FormaPagamento[]) response.body();
                }

                if (response.errorBody() != null){
                    utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), getCurrentFocus());
                }
  //              progressDialog.hide();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), getCurrentFocus());
                //progressDialog.hide();
            }
        });
    }

}
