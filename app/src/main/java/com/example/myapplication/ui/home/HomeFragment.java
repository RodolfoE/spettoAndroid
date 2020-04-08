package com.example.myapplication.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adaptador.ListaDonoDoPedido;
import com.example.myapplication.EscolherProdutoActivity;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Cliente;
import com.example.myapplication.modelos.ClienteDelivery;
import com.example.myapplication.modelos.Mesa;
import com.example.myapplication.servico.Chamadas;
import com.example.myapplication.util.utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ListaDonoDoPedido.ItemClickListener {
    private RecyclerView mRecyclerView;
    private HomeViewModel homeViewModel;
    private View mRoot;
    private HomeFragment mCtx = this;
    private boolean obterMesa = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_home1, container, false);
        initViews();
        if (obterMesa){
            obterMesa = false;
            obterMesas();
        }
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (obterMesa){
            obterMesa = false;
            obterMesas();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initViews(){
        mRecyclerView = mRoot.findViewById(R.id.my_recycler_view2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //dar onclick para o botao de cadastrar cliente
        mRoot.findViewById(R.id.cadastrarBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceAddNovoCliente();
            }
        });

        /*
        ///Add cliente
        mRoot.findViewById(R.id.addCliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View item = mRoot.findViewById(R.id.layout_mesa);
                if (item.getVisibility() == View.GONE){
                    //Exibir layout cliente e esconder mesa
                    item.setVisibility(View.GONE);
                    mRoot.findViewById(R.id.layout_cliente).setVisibility(View.VISIBLE);
                }
            }
        });*/

        //dar onclick para o botao de cadastrar cliente
        mRoot.findViewById(R.id.cadastrarMesa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceAddNovaMesa(mRoot, mRoot.findViewById(R.id.layout_mesa), mRecyclerView);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mais_mesa:
                exibirAddrMesa();
                return true;
            default:
                break;
        }
        return false;
    }

    private void exibirAddrMesa(){
        View item = mRoot.findViewById(R.id.layout_mesa);
        if (item.getVisibility() == View.GONE){
            //Exibir layout cliente e esconder mesa
            mRoot.findViewById(R.id.layout_mesa).setVisibility(View.VISIBLE);
            mRoot.findViewById(R.id.layout_cliente).setVisibility(View.GONE);
            if(mRoot.findViewById(R.id.numMesa).requestFocus()) {

            }

        } else {
            item.setVisibility(View.GONE);
        }
    }

    private void interfaceAddNovaMesa(final View view, final View layoutMesa, final RecyclerView recyclerView){
        final ProgressDialog progressDialog = utils.showLoading(mRoot.getContext(), "Adicionando cliente");
        String novaMesa = ((EditText) view.findViewById(R.id.numMesa)).getText().toString();
        try{
            if (novaMesa.length() == 0){
                throw new Exception("Não é possível adicionar mesa com número vazio.");
            }
            final Mesa mesa = new Mesa();
            mesa.setIdMesa(Integer.parseInt(novaMesa));
            Chamadas.postMesa(mesa, new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.body() != null){
                        int novaId = ((Mesa) response.body()).getIdDono();
                        mesa.setIdDono(novaId);
                        ((ListaDonoDoPedido) recyclerView.getAdapter()).add(mesa);
                        (recyclerView.getAdapter()).notifyDataSetChanged();
                        layoutMesa.setVisibility(View.GONE);
                        ((EditText) view.findViewById(R.id.numMesa)).setText("");
                    }

                    if (response.errorBody() != null){
                        utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
                    }
                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
                    progressDialog.hide();
                }
            });
        } catch (Exception e){
            utils.tratamentoDeErroPadrao(e, mRoot);
        }

    }

    private Cliente obterDadosCadastroCliente() throws Exception {
        String nome = ((EditText) mRoot.findViewById(R.id.nome)).getText().toString();
        String telefone = ((EditText) mRoot.findViewById(R.id.tel)).getText().toString();
        int fiel = ((CheckBox) mRoot.findViewById(R.id.checkbox_fiel)).isChecked() ? 1 : 0;

        if (nome.length() == 0){
            throw new Exception("Não é possível cadastrar cliente com nome vazio.");
        }
        return new Cliente(nome, telefone, fiel);
    }

    private void interfaceAddNovoCliente(){
        final ProgressDialog progressDialog = utils.showLoading(mRoot.getContext(), "Adicionando cliente");
        try{
            //obter dados inseridos
            final Cliente cliente = obterDadosCadastroCliente();
            Chamadas.postCliente(cliente, new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.body() != null){
                        int novaId = ((Cliente) response.body()).getIdDono();
                        cliente.setIdDono(novaId);
                        cliente.setIdMesa(novaId);
                        ((ListaDonoDoPedido) mRecyclerView.getAdapter()).addCliente(cliente);
                        (mRecyclerView.getAdapter()).notifyDataSetChanged();
                    }

                    if (response.errorBody() != null){
                        utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
                    }
                    progressDialog.hide();
                }
                @Override
                public void onFailure(Call call, Throwable t) {
                    utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
                    progressDialog.hide();
                }
            });
        } catch (Exception e){
            utils.tratamentoDeErroPadrao(e, mRoot.findViewById(android.R.id.content));
        }
    }

    private void interfaceObterDonoPedidos(Mesa[] mesa, Cliente[] cliente, ClienteDelivery[] clienteDeliveries, ProgressDialog progressDialog){
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
            progressDialog.hide();
            obterMesa = true;
        } catch (Exception e){
            utils.tratamentoDeErroPadrao(e, mRoot.findViewById(android.R.id.content));
        }
    }

    private void obterMesas(){
        final ProgressDialog progressDialog = utils.showLoading(mRoot.getContext(), "Adicionando cliente");
        Chamadas.obterMesas(-1, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null){
                    Mesa[] mesa = (Mesa[]) response.body();
                    obterClientes(mesa, progressDialog);
                }

                if (response.errorBody() != null){
                    utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
            }
        });
    }

    private void obterClientes(final Mesa[] mesa, final ProgressDialog progressDialog){
        Chamadas.obterClientes(-1, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null){
                    Cliente[] cliente = (Cliente[]) response.body();
                    obterClientesDelivery(mesa, cliente, progressDialog);
                }

                if (response.errorBody() != null){
                    utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
            }
        });
    }

    private void obterClientesDelivery(final Mesa[] mesa,final Cliente[] cliente, final ProgressDialog progressDialog){
        Chamadas.obterClientesDelivery(-1, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null){
                    ClienteDelivery[] clienteDeliveries = (ClienteDelivery[]) response.body();
                    interfaceObterDonoPedidos(mesa, cliente, clienteDeliveries, progressDialog);
                }

                if (response.errorBody() != null){
                    utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.tratamentoDeErroPadrao(new Exception("Ocorreu um erro."), mRoot);
            }
        });
    }

    @Override
    public void abrirItensPedido(int position) {
        Intent intent = new Intent(getContext(), EscolherProdutoActivity.class);
        Object item = ((ListaDonoDoPedido) mRecyclerView.getAdapter()).getItem(position);
        if (item instanceof Mesa){
            intent.putExtra("tipo_dono", "mesa");
            intent.putExtra("id_dono", ((Mesa) item).getIdDono() + "");
            intent.putExtra("id_pedido", ((Mesa) item).getIdPedido() + "");
            intent.putExtra("id_mesa", ((Mesa) item).getIdMesa() + "");
        } else if(item instanceof Cliente) {
            intent.putExtra("id_pedido", ((Cliente) item).getIdPedido() + "");
        } else {
            intent.putExtra("id_pedido", ((ClienteDelivery) item).getIdPedido() + "");
        }
        startActivity(intent);
    }
}