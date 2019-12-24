package com.example.myapplication;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_escolher_produto);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            handleIntent(getIntent());

            mRecyclerView = findViewById(R.id.my_recycler_view2);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            if (mProdutos == null)
                listagemProdutos(this);

            findViewById(R.id.fazer_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realizarPedido();
                }
            });
        } catch (Exception e){
            Log.e("Erro", e.getMessage());
        }
    }

    private void listagemProdutos(final EscolherProdutoActivity ctx) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);
        Call call = produtos.getProdutos();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try{
                    if (response.body() != null) {
                        Produto[] produto= (Produto[]) response.body();
                        mProdutos = produto;
                        adapter = new EscolherProdutoAdaptador(ctx, produto, ctx);
                        mRecyclerView.setAdapter(adapter);
                    }
                } catch (Exception e){
                    Log.e("Erro", "Deu ruim");
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("Erro", "Deu ruim");
            }
        });
    }

    @Override
    public void addProduto(View view, int position) {
        Produto item = adapter.getItem(position);
        item.setQtd(item.getQtd() + 1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void removerProduto(View view, int position) {
        Produto item = adapter.getItem(position);
        int qtd = item.getQtd() - 1 < 0 ? 0 : item.getQtd() - 1;
        item.setQtd(qtd);
        adapter.notifyDataSetChanged();
    }

    private void realizarPedido(){
        Produto[] produtos = adapter.obterItensSelecionados();
        ItensPedido itens = new ItensPedido(produtos, "Mesa", 1, 1);
        postarProduto(itens);
    }

    private void postarProduto(ItensPedido itens){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI produtos = retrofit.create(EscolherProdutosAPI.class);

        Call call = produtos.postProduto(itens);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try{
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Pedido realizado com sucesso.", Toast.LENGTH_LONG);
                    }
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Falha ao realizar pedido", Toast.LENGTH_LONG);
                    Log.e("Erro", "Deu ruim");
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("Erro", "Deu ruim");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }


    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void doSearch(String queryStr) {
        Log.i("Your search: ",queryStr);
    }
}
