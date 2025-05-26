package com.example.shoppingwise2;

import android.content.Context;
import android.content.Intent;
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

        // Formata e define a data
        holder.txtData.setText(formatarData(historia.getData()));

        // Define o texto de comparação (você pode personalizar isso)
        holder.txtPreco.setText("Toque para ver preços comparados");

        // Adiciona clique no item para abrir os detalhes do produto
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowPrice.class);
            intent.putExtra("produto", historia.getNome());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historiaList.size();
    }

    public void atualizarLista(List<Historia> novaLista) {
        this.historiaList = novaLista;
        notifyDataSetChanged();
    }

    private String formatarData(String dataString) {
        if (dataString == null || dataString.isEmpty()) {
            return "Data desconhecida";
        }

        try {
            // Formato da data vinda do Supabase: "2025-05-26T19:24:47.123456+00:00"
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

            // Remove a parte dos microsegundos e timezone se existir
            String dataLimpa = dataString.split("\\.")[0]; // Remove .123456+00:00

            Date data = formatoEntrada.parse(dataLimpa);

            // Verifica se é hoje
            Date hoje = new Date();
            SimpleDateFormat formatoDia = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            if (formatoDia.format(data).equals(formatoDia.format(hoje))) {
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
                return "Hoje às " + formatoHora.format(data);
            } else {
                return formatoSaida.format(data);
            }

        } catch (ParseException e) {
            // Se não conseguir fazer parse, retorna a data original
            return dataString.substring(0, Math.min(10, dataString.length())); // Apenas a parte da data
        }
    }

    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView txtData, txtProduto, txtPreco;

        public HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.txtData);
            txtProduto = itemView.findViewById(R.id.txtProduto);
            txtPreco = itemView.findViewById(R.id.txtPreco);
        }
    }
}