package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adaptador.ListaDonoDoPedido;
import com.example.myapplication.DonoDoPedido;
import com.example.myapplication.EscolherProdutoActivity;
import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Cliente;
import com.example.myapplication.modelos.ClienteDelivery;
import com.example.myapplication.modelos.ItensPedidoFeito;
import com.example.myapplication.modelos.Mesa;
import com.example.myapplication.servico.Chamadas;
import com.example.myapplication.util.utils;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements ListaDonoDoPedido.ItemClickListener {
    private RecyclerView mRecyclerView;
    private HomeViewModel homeViewModel;
    private View mRoot;
    private HomeFragment mCtx = this;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        obterMesas();
        return mRoot;
    }

    private void initViews(){
        mRecyclerView = mRoot.findViewById(R.id.my_recycler_view2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void interfaceObterDonoPedidos(Mesa[] mesa, Cliente[] cliente, ClienteDelivery[] clienteDeliveries){
        try{
            Object[] itens = new Object[mesa.length + cliente.length + clienteDeliveries.length];
            int counter = 0;
            for (int i = 0; i < mesa.length; i++){
                itens[counter++] = mesa[i];
            }
            for (int i = 0; i < cliente.length; i++){
                itens[counter++] = cliente[i];
            }
            for (int i = 0; i < clienteDeliveries.length; i++){
                itens[counter++] = clienteDeliveries[i];
            }
            ListaDonoDoPedido adapter = new ListaDonoDoPedido(mCtx, itens, mCtx);
            mRecyclerView.setAdapter(adapter);
        } catch (Exception e){
            utils.tratamentoDeErroPadrao(e, mRoot.findViewById(android.R.id.content));
        }
    }

    private void obterMesas(){
        Chamadas.obterMesas(-1, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null){
                    Mesa[] mesa = (Mesa[]) response.body();
                    obterClientes(mesa);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot.findViewById(android.R.id.content));
            }
        });
    }

    private void obterClientes(final Mesa[] mesa){
        Chamadas.obterClientes(-1, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null){
                    Cliente[] cliente = (Cliente[]) response.body();
                    obterClientesDelivery(mesa, cliente);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot.findViewById(android.R.id.content));
            }
        });
    }

    private void obterClientesDelivery(final Mesa[] mesa,final Cliente[] cliente){
        Chamadas.obterClientesDelivery(-1, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null){
                    ClienteDelivery[] clienteDeliveries = (ClienteDelivery[]) response.body();
                    interfaceObterDonoPedidos(mesa, cliente, clienteDeliveries);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot.findViewById(android.R.id.content));
            }
        });
    }


    @Override
    public void abrirItensPedido(int position) {
        Intent intent = new Intent(getContext(), EscolherProdutoActivity.class);
        Object item = ((ListaDonoDoPedido) mRecyclerView.getAdapter()).getItem(position);
        if (item instanceof Mesa){
            intent.putExtra("id_pedido", ((Mesa) item).getIdPedido());
        } else if(item instanceof Cliente) {
            intent.putExtra("id_pedido", ((Cliente) item).getIdPedido());
        } else {
            intent.putExtra("id_pedido", ((Mesa) item).getIdPedido());
        }
        startActivity(intent);
    }
}