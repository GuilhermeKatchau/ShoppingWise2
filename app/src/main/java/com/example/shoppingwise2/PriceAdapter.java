package com.example.shoppingwise2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingwise2.Produto;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    private final List<Produto> productList;

    public PriceAdapter(List<Produto> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_price, parent, false);
        return new PriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {
        Produto produto = productList.get(position);
        holder.storeTextView.setText(produto.getLoja());
        holder.priceTextView.setText(produto.getPreco());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class PriceViewHolder extends RecyclerView.ViewHolder {
        TextView storeTextView, priceTextView;

        public PriceViewHolder(@NonNull View itemView) {
            super(itemView);
            storeTextView = itemView.findViewById(R.id.txtLoja);
            priceTextView = itemView.findViewById(R.id.txtPreco);
        }
    }
}