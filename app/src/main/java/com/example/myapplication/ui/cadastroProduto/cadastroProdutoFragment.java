package com.example.myapplication.ui.cadastroProduto;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.HttpRequests.EscolherProdutosAPI;
import com.example.myapplication.HttpRequests.NetworkClient;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Praca;
import com.example.myapplication.modelos.Produto;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class cadastroProdutoFragment extends Fragment {
    private View mRoot;
    private Praca[] mPracas;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cadastro_produto, container, false);
        mRoot = root;
        obterPracas();

        ((Button) root.findViewById(R.id.btnCadastrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCadastro();
            }
        });

        return root;
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

    public void salvarCadastro(){
        Produto produto = new Produto();
        Spinner spinner = mRoot.findViewById(R.id.spinner);
        TextView textView = (TextView) spinner.getSelectedView();
        String result = textView.getText().toString();
        for (int i = 0; i < mPracas.length; i++) {
            Praca pra = mPracas[i];
            if (pra.getNome().equals(result))
                produto.setIdPraca(pra.getId_praca());
        }
        produto.setNome(((TextView) mRoot.findViewById(R.id.nomeProduto)).getText().toString());
        produto.setValor(((TextView) mRoot.findViewById(R.id.precoProduto)).getText().toString());
        produto.setValor_de_custo(((TextView) mRoot.findViewById(R.id.precoCustoProduto)).getText().toString());
        produto.setCategoria(((TextView) mRoot.findViewById(R.id.categoriaProduto)).getText().toString());

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        EscolherProdutosAPI lib = retrofit.create(EscolherProdutosAPI.class);
        Call call;
        call = lib.cadastrarProduto(produto);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                Snackbar mySnackbar = Snackbar.make(mRoot.getRootView().findViewById(android.R.id.content), "Produto Cadastrado", 3000);
                mySnackbar.show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Snackbar mySnackbar = Snackbar.make(mRoot.getRootView().findViewById(android.R.id.content), "Erro | Produto Não Cadastrado", 3000);
                mySnackbar.show();
                Log.e("Erro", t.getMessage());
            }
        });
    }

    private void initPracas(){
        String[] nomeDasPracas = new String[mPracas.length];
        for (int i = 0; i < mPracas.length; i++) {
            nomeDasPracas[i] = mPracas[i].getNome();
        }
        Spinner spinner = mRoot.findViewById(R.id.spinner);
        spinner.setPrompt("saljasldfj");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mRoot.getContext(),
                android.R.layout.simple_spinner_item, nomeDasPracas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
