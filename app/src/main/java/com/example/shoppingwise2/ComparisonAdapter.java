package com.example.shoppingwise2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class ComparisonAdapter extends RecyclerView.Adapter<ComparisonAdapter.ProdutoViewHolder> {

    private final List<Produto> listaProdutos;
    private final Context context;

    public ComparisonAdapter(List<Produto> listaProdutos, Context context) {
        this.listaProdutos = listaProdutos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comparison_item, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);

        // Preenche os dados do produto
        holder.txtNomeProduto.setText(produto.getNome());
        holder.txtCodigoBarras.setText(String.format("Código: %s", produto.getBarcode()));

        // Carrega a imagem do produto
        if (produto.getUrl_imagem() != null && !produto.getUrl_imagem().isEmpty()) {
            Glide.with(context)
                    .load(produto.getUrl_imagem())
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .error(R.drawable.ic_facebook)
                    .into(holder.imgProduto);
        }

        // Limpa as lojas anteriores
        holder.containerLojas.removeAllViews();

        // Adiciona todas as lojas para este produto
        for (PrecoLoja loja : produto.getPrecosLojas()) {
            View itemLoja = LayoutInflater.from(context)
                    .inflate(R.layout.item_loja, holder.containerLojas, false);

            TextView txtNomeLoja = itemLoja.findViewById(R.id.txtNomeLoja);
            TextView txtPrecoLoja = itemLoja.findViewById(R.id.txtPrecoLoja);
            ImageView imgLogoLoja = itemLoja.findViewById(R.id.imgLogoLoja);

            txtNomeLoja.setText(loja.getLoja());
            txtPrecoLoja.setText(loja.getPreco());

            if (loja.getURL_ImagemLoja() != null && !loja.getURL_ImagemLoja().isEmpty()) {
                Glide.with(context)
                        .load(loja.getURL_ImagemLoja())
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .error(R.drawable.ic_google)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                Log.e("GlideError", "Falha ao carregar imagem: " + e.getMessage());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model,
                                                           Target<Drawable> target, DataSource dataSource,
                                                           boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(imgLogoLoja);
            }

            holder.containerLojas.addView(itemLoja);
        }
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto;
        TextView txtNomeProduto, txtCodigoBarras;
        ViewGroup containerLojas;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            txtNomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            txtCodigoBarras = itemView.findViewById(R.id.txtCodigoBarras);
            containerLojas = itemView.findViewById(R.id.containerLojas);
        }
    }

    // Metodo para atualizar a lista
    public void atualizarLista(List<Produto> novosProdutos) {
        listaProdutos.clear();
        listaProdutos.addAll(novosProdutos);
        notifyDataSetChanged();
    }
}