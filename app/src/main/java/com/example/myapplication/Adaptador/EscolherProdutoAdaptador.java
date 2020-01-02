package com.example.myapplication.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.EscolherProdutoActivity;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Produto;

import java.util.ArrayList;
import java.util.List;

public class EscolherProdutoAdaptador  extends RecyclerView.Adapter<EscolherProdutoAdaptador.MyViewHolder> {
    private Produto[] mDataset;
    private ItemClickListener mListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout relativeLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EscolherProdutoAdaptador(EscolherProdutoActivity escolherProdutoActivity, Produto[] myDataset, ItemClickListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    @Override
    public EscolherProdutoAdaptador.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.escolher_produto_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ((TextView) holder.relativeLayout.findViewById(R.id.nome)).setText(mDataset[position].getNome());
        ((TextView) holder.relativeLayout.findViewById(R.id.preco)).setText("R$" + mDataset[position].getValor());
        ((TextView) holder.relativeLayout.findViewById(R.id.qtd)).setText(mDataset[position].getQtd() + "");
        (holder.relativeLayout.findViewById(R.id.add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addProduto(v, position);
            }
        });

        (holder.relativeLayout.findViewById(R.id.remover)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.removerProduto(v, position);
            }
        });

    }

    // convenience method for getting data at click position
    public Produto getItem(int id) {
        return mDataset[id];
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void addProduto(View view, int position);
        void removerProduto(View view, int position);
    }

    public Produto[] obterItensSelecionados(){
        ArrayList<Produto> produtosFiltrados = new ArrayList<Produto>();
        for (Produto produto : mDataset) {
            if (produto.getQtd() > 0)
                produtosFiltrados.add(produto);
        }
        return produtosFiltrados.toArray(new Produto[produtosFiltrados.size()]);
    }
}
