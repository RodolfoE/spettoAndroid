package com.example.myapplication;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Adaptador.EscolherProdutoAdaptador;
import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.modelos.ItensPedido;
import com.example.myapplication.modelos.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EscolherProdutoActivity extends AppCompatActivity implements EscolherProdutoAdaptador.ItemClickListener {
    private EscolherProdutoAdaptador adapter;
    private RecyclerView  mRecyclerView;
    private Produto[] mProdutos;
    private Produto[] mProdutosSelecionados;
    private EscolherProdutoActivity mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("");
            setContentView(R.layout.activity_escolher_produto);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            if(savedInstanceState != null) {
                String json= savedInstanceState.getString("mProdutos");
                if(!json.isEmpty()) {
                    Gson gson = new Gson();
                    mProdutos = gson.fromJson(json, Produto[].class);
                }
            }
            initViews();
        } catch (Exception e){
            Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Ocorreu um erro.", 3000);
            mySnackbar.show();
            Log.e("Erro", e.getMessage());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String json= gson.toJson(mProdutos);
        outState.putString("mProdutos", json);
    }

    private void initViews(){
        mCtx = this;
        mRecyclerView = findViewById(R.id.my_recycler_view2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mProdutos == null)
            listagemProdutos(mCtx, null);
        else {
            adapter = new EscolherProdutoAdaptador(mCtx, mProdutos, mCtx);
            mRecyclerView.setAdapter(adapter);
        }

        findViewById(R.id.fazer_pedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarPedido();
            }
        });

        final EditText pesquisa = findViewById(R.id.pesquisar);
        pesquisa.setTag(false);
        pesquisa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pesquisa.setTag(hasFocus);
            }
        });
        pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((Boolean)((EditText)findViewById(R.id.pesquisar)).getTag()) {
                    listagemProdutos(mCtx, s.toString());
                }
            }

        });
    }

    private void listagemProdutos(final EscolherProdutoActivity ctx, String filtroNome) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        if (filtroNome == null || filtroNome == ""){
            call = produtos.getProdutos();
        } else {
            call = produtos.getProdutos("{\"nome\": \"" + filtroNome + "\"}");
        }

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try{
                    Log.e("Erro", response.body().toString());
                    if (response.body() != null) {
                        Produto[] produto= (Produto[]) response.body();
                        boolean manterEstadoApp = true;
                        if (mProdutos == null){
                            mProdutos = produto;
                            manterEstadoApp = false;
                        }
                        exibirProdutos(produto, manterEstadoApp);
                    }
                } catch (Exception e){
                    Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Ocorreu um erro ao obter lista de produtos.", 3000);
                    mySnackbar.show();
                    Log.e("Erro", e.getMessage());
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Ocorreu um erro ao obter lista de produtos.", 3000);
                mySnackbar.show();
                Log.e("Erro", t.getMessage());
            }
        });
    }

    /**
     * Exibe os produtos numa lista
     * @param produtos
     * @param manterEstadoApp mantem o estado da aplicação (evita iteração inicial desnecessária)
     */
    private void exibirProdutos(Produto[] produtos, boolean manterEstadoApp){
        if (manterEstadoApp)
            for (Produto produto : produtos) {
                for (Produto mProduto : mProdutos) {
                    if (mProduto.getIdProduto().equals(produto.getIdProduto()))
                        produto.setQtd(mProduto.getQtd());
                }
            }
        adapter = new EscolherProdutoAdaptador(mCtx, produtos, mCtx);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void addProduto(View view, int position) {
        findViewById(R.id.fazer_pedido).setEnabled(true);
        Produto item = adapter.getItem(position);
        int novaQtd = item.getQtd() + 1;
        for (Produto mProduto : mProdutos) {
            if (mProduto.getIdProduto().equals(item.getIdProduto()))
                mProduto.setQtd(novaQtd);
        }
        item.setQtd(novaQtd);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void removerProduto(View view, int position) {
        Produto item = adapter.getItem(position);
        int qtd = item.getQtd() - 1 < 0 ? 0 : item.getQtd() - 1;
        item.setQtd(qtd);
        adapter.notifyDataSetChanged();
        Produto[] produtos = adapter.obterItensSelecionados();
        if (produtos.length == 0)
            findViewById(R.id.fazer_pedido).setEnabled(false);
    }

    private void realizarPedido(){
        Produto[] produtos = adapter.obterItensSelecionados();
        ItensPedido itens = new ItensPedido(produtos, "mesa", 1, 1, 1);
        postarProduto(itens);
    }

    private void postarProduto(ItensPedido itens){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);

        Call call = produtos.postProduto(itens);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Carregando");
        progress.setMessage("Pedido está sendo processado...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try{
                    if (response.body() != null) {
                        Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Pedido realizado com sucesso.", 3000);
                        mySnackbar.show();
                        // To dismiss the dialog
                    }
                } catch (Exception e){
                    Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Ocorreu um erro ao realizar pedido.", 3000);
                    mySnackbar.show();
                    Log.e("Erro", e.getMessage());
                } finally {
                    progress.dismiss();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Ocorreu um erro ao realizar pedido.", 3000);
                mySnackbar.show();
                progress.dismiss();
                Log.e("Erro", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
