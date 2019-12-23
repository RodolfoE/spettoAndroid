package com.example.myapplication.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.EscolherProdutoActivity;
import com.example.myapplication.R;
import com.example.myapplication.modelos.Produto;

public class EscolherProdutoAdaptador  extends RecyclerView.Adapter<EscolherProdutoAdaptador.MyViewHolder> {
    private Produto[] mDataset;

    public void setClickListener(EscolherProdutoActivity escolherProdutoActivity) {
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EscolherProdutoAdaptador(EscolherProdutoActivity escolherProdutoActivity, Produto[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EscolherProdutoAdaptador.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.escolher_produto_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.textView.setText(mDataset[position]);
        ((TextView) holder.linearLayout.findViewById(R.id.nome)).setText(mDataset[position].getNome());
        ((TextView) holder.linearLayout.findViewById(R.id.preco)).setText(mDataset[position].getValor());
        ((TextView) holder.linearLayout.findViewById(R.id.qtd)).setText(mDataset[position].getQtd() + "");
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
        void onItemClick(View view, int position);
    }
}
