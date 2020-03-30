package com.example.myapplication.ui.Produto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adaptador.ListaProduto;
import com.example.myapplication.R;

public class Produto extends Fragment {
    private View mRoot;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_produto, container, false);
        initComponents();
        mRoot = root;
        return root;
    }

    private void initComponents(){
        initList();
    }

    private void initList(){
        mRecyclerView = mRoot.findViewById(R.id.lista_produtos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mRoot.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRoot.getContext()));
        ListaProduto adapter = new ListaProduto(null);
        mRecyclerView.setAdapter(adapter);
    }

}
