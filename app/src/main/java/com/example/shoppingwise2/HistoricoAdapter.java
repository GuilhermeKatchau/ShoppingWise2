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

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder>{
private List<Pesquisa> historicoList;
    public HistoricoAdapter(List<Pesquisa> historicoList) {
        this.historicoList = historicoList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtData, txtProduto, txtPreco;

        public ViewHolder(View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.txtData);
            txtProduto = itemView.findViewById(R.id.txtProduto);
            txtPreco = itemView.findViewById(R.id.txtPreco);
        }
    }
    @NonNull
    @Override
    public HistoricoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pesquisa item = historicoList.get(position);
        holder.txtData.setText(item.getData());
        holder.txtProduto.setText(item.getProduto());
        holder.txtPreco.setText(item.getComparacao());
    }

    @Override
    public int getItemCount() {
        return historicoList.size();
    }

}
