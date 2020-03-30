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
import com.example.myapplication.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaProduto extends RecyclerView.Adapter<ListaProduto.MyViewHolder>  {
    private ArrayList<Object> mDataset = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout relativeLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListaProduto(Object[] myDataset) {
        mDataset.addAll(Arrays.asList(myDataset));
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
