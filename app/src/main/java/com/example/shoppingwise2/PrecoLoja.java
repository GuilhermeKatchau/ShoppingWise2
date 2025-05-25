package com.example.shoppingwise2;

import com.google.gson.annotations.SerializedName;

public class PrecoLoja {
    @SerializedName("id_precoloja")
    private Integer id_precoloja;

    @SerializedName("id_produto")
    private Integer id_produto;

    @SerializedName("loja")
    private String loja;

    @SerializedName("preco")
    private String preco;

    @SerializedName("url_imagemloja")
    private String url_imagemloja;

    public PrecoLoja(String loja, String preco, String URL_ImagemLoja) {
        this.loja = loja;
        this.preco = preco;
        this.url_imagemloja = URL_ImagemLoja;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getLoja() {
        return loja;
    }

    public String getPreco() {
        return preco;
    }

    public String getURL_ImagemLoja() {
        return url_imagemloja;
    }
}
