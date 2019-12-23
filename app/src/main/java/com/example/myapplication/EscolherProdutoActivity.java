package com.example.myapplication;

import android.os.Bundle;

import com.example.myapplication.Adaptador.EscolherProdutoAdaptador;
import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.modelos.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EscolherProdutoActivity extends AppCompatActivity implements EscolherProdutoAdaptador.ItemClickListener {
    private EscolherProdutoAdaptador adapter;
    private RecyclerView  mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_escolher_produto);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            mRecyclerView = findViewById(R.id.my_recycler_view2);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            /*adapter = new EscolherProdutoAdaptador(this, new Produto[]{new Produto()});
            adapter.setClickListener(this);
            mRecyclerView .setAdapter(adapter);*/
            listagemProdutos(this);
        } catch (Exception e){
            Log.e("Erro", e.getMessage());
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
                        adapter = new EscolherProdutoAdaptador(ctx, produto);
                        adapter.setClickListener(ctx);
                        mRecyclerView .setAdapter(adapter);
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
}
