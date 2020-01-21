package com.example.myapplication.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DonoDoPedido;
import com.example.myapplication.EscolherProdutoActivity;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Cliente;
import com.example.myapplication.modelos.ClienteDelivery;
import com.example.myapplication.modelos.Mesa;
import com.example.myapplication.modelos.Produto;
import com.example.myapplication.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaDonoDoPedido extends RecyclerView.Adapter<ListaDonoDoPedido.MyViewHolder>  {
    private ArrayList<Object> mDataset = new ArrayList<>();
    private ItemClickListener mListener;
    private int VIEW_PEDIDO_MESA = 0;
    private int VIEW_PEDIDO_CLIENTE = 1;
    private int VIEW_PEDIDO_CLIENTE_DELIVERY = 2;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout relativeLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListaDonoDoPedido(HomeFragment homeFragment, Object[] myDataset, ItemClickListener listener) {
        mDataset.addAll(Arrays.asList(myDataset));
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position) instanceof Mesa) {
            return VIEW_PEDIDO_MESA;
        } else if (mDataset.get(position) instanceof Cliente) {
            return VIEW_PEDIDO_CLIENTE;
        } else {
            return VIEW_PEDIDO_CLIENTE_DELIVERY;
        }
    }

    @Override
    public ListaDonoDoPedido.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v;
        if (viewType == VIEW_PEDIDO_MESA){
            v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pedido_mesa_row, parent, false);
        }
        else if (viewType == VIEW_PEDIDO_CLIENTE){
            v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pedido_cliente_row, parent, false);
        } else {
            v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pedido_cliente_delivery_row, parent, false);
        }
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Object item = mDataset.get(position);
        if (mDataset.get(position) instanceof Mesa){
            ((TextView) holder.relativeLayout.findViewById(R.id.nome)).setText(((Mesa) item).getIdMesa() + "");
            ((ImageButton) holder.relativeLayout.findViewById(R.id.disponivel)).setImageResource(((Mesa) item).getEmUso() == 1 ? R.drawable.disponivel : R.drawable.indisponivel);
            (holder.relativeLayout.findViewById(R.id.disponivel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.abrirItensPedido(position);
                }
            });
        } else if (mDataset.get(position) instanceof Cliente){
            ((TextView) holder.relativeLayout.findViewById(R.id.nome)).setText(( (Cliente) mDataset.get(position)).getNome() + "");
            ((ImageButton) holder.relativeLayout.findViewById(R.id.disponivel)).setImageResource(((Cliente) item).getEmUso() == 1 ? R.drawable.disponivel : R.drawable.indisponivel);
        } else {
            ((TextView) holder.relativeLayout.findViewById(R.id.nome)).setText(( (ClienteDelivery) mDataset.get(position)).getNome() + "");
            ((ImageButton) holder.relativeLayout.findViewById(R.id.disponivel)).setImageResource(((ClienteDelivery) item).getEmUso() == 1 ? R.drawable.disponivel : R.drawable.indisponivel);
        }
        holder.itemView.bringToFront();
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.abrirItensPedido(position);
            }
        });
    }

    public void addCliente(Cliente cli){
        this.mDataset.add(cli);
    }
    public void add(Object i){
        this.mDataset.add(0, i);
    }

    // convenience method for getting data at click position
    public Object getItem(int id) {
        return mDataset.get(id);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        //void addProduto(View view, int position);
        void abrirItensPedido(int position);
    }

}
