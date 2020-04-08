package com.example.myapplication.ui.cadastroProduto;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Categoria;
import com.example.myapplication.modelos.Praca;
import com.example.myapplication.modelos.Produto;
import com.example.myapplication.util.utils;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class cadastroProdutoFragment extends Fragment {
    private Praca[] mPracas;
    private Categoria[] mCategorias;
    private String mIdProduto = "";
    private View mRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_cadastro_produto, container, false);
        String idProduto = getArguments().getString("ID_PRODUTO");
        if (idProduto != null)
            obterProduto(idProduto);
        else
            initViews(mRoot, null);
        return mRoot;
    }

    private void initViews(final View view, final Produto produto){
        obterPracas();
        obterCategorias();

        if (produto != null){
            ((Button) view.findViewById(R.id.btnCadastrar)).setText("Atualizar");
            ((EditText) view.findViewById(R.id.nomeProduto)).setText(produto.getNome());
            ((EditText) view.findViewById(R.id.precoProduto)).setText(produto.getValor());
            ((EditText) view.findViewById(R.id.precoCustoProduto)).setText(produto.getValor_de_custo());
            ((EditText) view.findViewById(R.id.qtdEstoqueProduto)).setText(produto.getEstoque() + "");
        }

        view.findViewById(R.id.btnCadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.esconderTeclado(getView());
                if (produto != null)
                    atualizarCadastro();
                else
                    salvarCadastro();
            }
        });

        view.findViewById(R.id.btnCadastrarPraca).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View child = getLayoutInflater().inflate(R.layout.cadastro_praca, null);
                final Dialog dialog = utils.openDialog("Cadastrar Praça", child, getContext());
                (child.findViewById(R.id.btnCadastrarPraca)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nomePraca = ((EditText) child.findViewById(R.id.editNomePraca)).getText().toString();
                        Praca praca = new Praca();
                        praca.setId_responsavel(1);
                        praca.setNome(nomePraca);
                        cadastrarPraca(praca, new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.errorBody() == null){
                                    utils.esconderTeclado(child);
                                    dialog.dismiss();
                                    Snackbar mySnackbar = Snackbar.make(view.findViewById(android.R.id.content), "Praça cadastrada", 3000);
                                    mySnackbar.show();
                                    obterPracas();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Snackbar mySnackbar = Snackbar.make(view.findViewById(android.R.id.content), "Erro | Praça Não Cadastrado", 3000);
                                mySnackbar.show();
                                Log.e("Erro", t.getMessage());
                            }
                        });
                    }
                });
            }
        });

        view.findViewById(R.id.btnCadastrarCategoria).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View child = getLayoutInflater().inflate(R.layout.cadastro_categoria, null);
                final Dialog dialog = utils.openDialog("Cadastrar Praça", child, getContext());
                (child.findViewById(R.id.btnCadastrarCategoria)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nomeCategoria = ((EditText) child.findViewById(R.id.editNomeCategoria)).getText().toString();
                        Categoria categoria = new Categoria();
                        categoria.setNome(nomeCategoria);
                        cadastrarCategoria(categoria, new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.errorBody() == null){
                                    utils.esconderTeclado(child);
                                    dialog.dismiss();
                                    Snackbar mySnackbar = Snackbar.make(view.findViewById(android.R.id.content), "Categoria cadastrada", 3000);
                                    mySnackbar.show();
                                    obterCategorias();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Snackbar mySnackbar = Snackbar.make(view.findViewById(android.R.id.content), "Erro | Categoria Não Cadastrado", 3000);
                                mySnackbar.show();
                                Log.e("Erro", t.getMessage());
                            }
                        });
                    }
                });
            }
        });
    }

    private void atualizarCadastro(){

    }

    private void cadastrarCategoria(Categoria categoria, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI lib = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        call = lib.cadastrarCategoria(categoria);
        call.enqueue(callback);
    }

    private void cadastrarPraca(Praca praca, Callback callback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI lib = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        call = lib.cadastrarPraca(praca);
        call.enqueue(callback);
    }

    private void obterPracas(){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI lib = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        call = lib.obterPracas();
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {
                    mPracas = (Praca[]) response.body();
                    initPracas();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Snackbar mySnackbar = Snackbar.make(mRoot.findViewById(android.R.id.content), "Ocorreu um erro ao obter lista de produtos.", 3000);
                mySnackbar.show();
                Log.e("Erro", t.getMessage());
            }
        });
    }

    private void obterCategorias(){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI lib = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        call = lib.obterCategorias();
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {
                    mCategorias = (Categoria[]) response.body();
                    initCategorias();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Snackbar mySnackbar = Snackbar.make(mRoot.findViewById(android.R.id.content), "Ocorreu um erro ao obter lista de produtos.", 3000);
                mySnackbar.show();
                Log.e("Erro", t.getMessage());
            }
        });
    }

    public void salvarCadastro(){
        try{
            Produto produto = new Produto();
            Spinner spinner = mRoot.findViewById(R.id.spinner);
            TextView textView = (TextView) spinner.getSelectedView();
            String result = textView.getText().toString();
            for (int i = 0; i < mPracas.length; i++) {
                Praca pra = mPracas[i];
                if (pra.getNome().equals(result))
                    produto.setIdPraca(pra.getId_praca());
            }

            Spinner spinnerCat = mRoot.findViewById(R.id.spinnerCategoria);
            TextView textViewcat = (TextView) spinnerCat.getSelectedView();
            String resultCat = textViewcat.getText().toString();
            for (int i = 0; i < mCategorias.length; i++) {
                Categoria cat = mCategorias[i];
                if (cat.getNome().equals(resultCat))
                    produto.setCategoria(cat.getId_categoria());
            }

            produto.setNome(((TextView) mRoot.findViewById(R.id.nomeProduto)).getText().toString());
            produto.setValor(((TextView) mRoot.findViewById(R.id.precoProduto)).getText().toString());
            produto.setValor_de_custo(((TextView) mRoot.findViewById(R.id.precoCustoProduto)).getText().toString());
            produto.setEstoque(Integer.parseInt(((TextView) mRoot.findViewById(R.id.qtdEstoqueProduto)).getText().toString()));

            if (produto.getNome().isEmpty() || produto.getValor().isEmpty() || produto.getValor_de_custo().isEmpty())
                throw new Exception("Os campos 'Nome', 'Valor' e 'Valor de Custo' não podem estar vazios.");

            Retrofit retrofit = NetworkClient.getRetrofitClient();
            EscolherProdutosAPI lib = retrofit.create(EscolherProdutosAPI.class);
            Call call;
            call = lib.cadastrarProduto(produto);
            call.enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.errorBody() == null){
                        Snackbar mySnackbar = Snackbar.make(mRoot.findViewById(android.R.id.content), "Produto Cadastrado", 3000);
                        mySnackbar.show();
                    } else {
                        Snackbar mySnackbar = Snackbar.make(mRoot.findViewById(android.R.id.content), "Produto não cadastrado", 3000);
                        mySnackbar.show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Snackbar mySnackbar = Snackbar.make(mRoot.findViewById(android.R.id.content), "Erro | Produto Não Cadastrado", 3000);
                    mySnackbar.show();
                    Log.e("Erro", t.getMessage());
                }
            });
        } catch (Exception e){
            Snackbar mySnackbar = Snackbar.make(mRoot.findViewById(android.R.id.content), e.getMessage(), 3000);
            mySnackbar.show();
            Log.e("Erro", e.getMessage());
        }
    }

    private void initPracas(){
        String[] nomeDasPracas = new String[mPracas.length];
        for (int i = 0; i < mPracas.length; i++) {
            nomeDasPracas[i] = mPracas[i].getNome();
        }
        Spinner spinner = mRoot.findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, nomeDasPracas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void initCategorias(){
        String[] nomeDasCategorias = new String[mCategorias.length];
        for (int i = 0; i < mCategorias.length; i++) {
            nomeDasCategorias[i] = mCategorias[i].getNome();
        }
        Spinner spinner = mRoot.findViewById(R.id.spinnerCategoria);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, nomeDasCategorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void obterProduto(String idProduto){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI lib = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        call = lib.getProdutos("{\"p.id_produto\": \"" + idProduto + "\"}");
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {
                    Produto produto = ((Produto[]) response.body()).length > 0 ? ((Produto[]) response.body())[0] : null;
                    initViews(mRoot, produto);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Snackbar mySnackbar = Snackbar.make(mRoot.findViewById(android.R.id.content), "Ocorreu um erro ao obter produto.", 3000);
                mySnackbar.show();
                Log.e("Erro", t.getMessage());
            }
        });
    }
}
