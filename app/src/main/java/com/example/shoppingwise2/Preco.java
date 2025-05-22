package com.example.shoppingwise2;

public class Preco {
    private int id;
    private String loja;
    private float valor;
    private String URL_Produto;

    Preco(int id, String loja, float valor, String URL_Produto) {
        this.id = id;
        this.loja = loja;
        this.valor = valor;
        this. URL_Produto = URL_Produto;
    }

    public int getId() {
        return id;
    }

    public String getLoja() {
        return loja;
    }

    public float getPreco() {
        return valor;
    }

    public String getURL_Produto() {
        return URL_Produto;
    }
}
