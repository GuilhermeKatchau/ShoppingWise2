package com.example.shoppingwise2;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ComparisonAdapter extends RecyclerView.Adapter<ComparisonAdapter.ViewHolder> {

    private List<Produto> listaProdutos;

    public ComparisonAdapter(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLoja, txtPreco;

        public ViewHolder(View itemView) {
            super(itemView);
            txtLoja = itemView.findViewById(R.id.txtLoja);
            txtPreco = itemView.findViewById(R.id.txtPreco);
        }
    }

    @NonNull
    @Override
    public ComparisonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comparison_item, parent, false);
        return new ComparisonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComparisonAdapter.ViewHolder holder, int position) {
        Produto item = listaProdutos.get(position);
        holder.txtLoja.setText(item.getLoja());
        holder.txtPreco.setText(item.getPreco());
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

}
