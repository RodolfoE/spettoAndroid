package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.modelos.FormaPagamento;
import com.example.myapplication.modelos.Venda;
import com.example.myapplication.util.utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FecharPedido extends AppCompatActivity {
    private int mIdForma;
    private AlertDialog mDialog;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initViews();
    }

    private void initViews(){
        obterFormasDePagamento();
    }

    public void fecharCompleto(View v){
        int idPedido = getIntent().getIntExtra("id_pedido", -1);
        int forma = getIntent().getIntExtra("forma", -1);
        String total = getIntent().getStringExtra("total");
        String totalParcial = ((TextView)findViewById(R.id.totalParcial)).getText().toString();
        final boolean fechado = true;
        fechar(new Venda(idPedido, Double.parseDouble(total), Double.parseDouble(totalParcial), fechado, forma));
    }

    public void fecharParcial(View v){
        int idPedido = getIntent().getIntExtra("id_pedido", -1);
        int forma = getIntent().getIntExtra("forma", -1);
        String total = getIntent().getStringExtra("total");
        String totalParcial = ((TextView)findViewById(R.id.totalParcial)).getText().toString();
        final boolean fechado = false;
        fechar(new Venda(idPedido, Double.parseDouble(total), Double.parseDouble(totalParcial), fechado, forma));
    }

    private void fechar(final Venda venda){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);

        Call call = produtos.fecharPedido(venda);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("fechado", venda.isFechado());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), getCurrentFocus());
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
                try{
                    FormaPagamento[] formas;
                    if (response.body() != null){
                        formas = (FormaPagamento[]) response.body();
                        mDialog = dialogOpcoesPagamento(formas);
                    }

                    if (response.errorBody() != null){
                        utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), getCurrentFocus());
                    }
                } catch (Exception e){
                    View rootView = ((Activity)getBaseContext()).getWindow().getDecorView().findViewById(android.R.id.content);
                    utils.tratamentoDeErroPadrao(e, rootView);
                }

  //
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), getCurrentFocus());
            }
        });
    }

    private AlertDialog dialogOpcoesPagamento(final FormaPagamento[] forma){
        final String[] formas = FormaPagamento.obterListaDeFormas(forma);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione a forma de pagamento")
                .setItems(formas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIdForma = forma[which].getId_forma();
                    }
                });

         return builder.create();
    }


}
