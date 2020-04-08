package com.example.myapplication.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.modelos.Cliente;
import com.example.myapplication.modelos.ClienteDelivery;
import com.example.myapplication.modelos.Mesa;
import com.example.myapplication.modelos.Produto;
import com.example.myapplication.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaProduto extends RecyclerView.Adapter<ListaProduto.MyViewHolder>  {
    private listaProdutoClick mListaProdutoClick;
    private ArrayList<Produto> mDataset = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout relativeLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    public ListaProduto(Produto[] myDataset, listaProdutoClick listaProdutoClick) {
        mDataset.addAll(Arrays.asList(myDataset));
        this.mListaProdutoClick = listaProdutoClick;
    }

    @Override
    public ListaProduto.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v;
        v = (LinearLayout) LayoutInflater.from(parent.getContext())
               .inflate(R.layout.produto_unitario, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ((TextView)holder.relativeLayout.findViewById(R.id.produto_nome)).setText(mDataset.get(position).getNome());
        ((TextView)holder.relativeLayout.findViewById(R.id.produto_preco)).setText("R$ " + mDataset.get(position).getValor());
        ((TextView)holder.relativeLayout.findViewById(R.id.produto_qtd)).setText(mDataset.get(position).getQtd() + "");
        //((TextView)holder.relativeLayout.findViewById(R.id.produto_praca)).setText(mDataset.get(position).getIdPraca());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListaProdutoClick.onClickProduto(mDataset.get(position).getIdProduto());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface listaProdutoClick{
        public void onClickProduto(String idProduto);
    }
}
