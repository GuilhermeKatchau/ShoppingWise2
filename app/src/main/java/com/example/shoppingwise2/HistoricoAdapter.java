package com.example.shoppingwise2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder> {

    private List<Historia> historiaList;
    private Context context;

    public HistoricoAdapter(List<Historia> historiaList, Context context) {
        this.historiaList = historiaList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoricoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoViewHolder holder, int position) {
        Historia historia = historiaList.get(position);

        // Define o nome do produto
        holder.txtProduto.setText(historia.getNome());

        Log.d("Adapter", "Produto: " + historia.getNome() + " | Data bruta: " + historia.getData());
        // Formata e define a data
        holder.txtData.setText(historia.getData());

        Glide.with(context)
                .load(historia.getImagem())
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_google)
                .into(holder.imgProduto);
    }

    @Override
    public int getItemCount() {
        return historiaList.size();
    }

    public void atualizarLista(List<Historia> novaLista) {
        this.historiaList = novaLista;
        notifyDataSetChanged();
    }



    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView txtData, txtProduto;
        ImageView imgProduto;

        public HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProduto = itemView.findViewById(R.id.txtProduto);
            txtData = itemView.findViewById(R.id.txtData);
            imgProduto = itemView.findViewById(R.id.imgProduto);
        }
    }
}