package com.example.shoppingwise2;

import java.util.List;
public class Produto {

    private int id;
    private String nome;
    private String barcode;
    private String URL_Imagem;
    private List<Preco> listaPreco;
    private List<Avaliacao> listaAvaliacao;

    private String loja;
    private String preco;

    public Produto(int id, String nome, String barcode, String URL_Imagem, List<Preco> listaPreco, List<Avaliacao> listaAvaliacao, String loja, String preco) {
        this.id = id;
        this.nome = nome;
        this.barcode = barcode;
        this.URL_Imagem = URL_Imagem;
        this.listaPreco = listaPreco;
        this.listaAvaliacao = listaAvaliacao;
        this.loja = loja;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getBarcode() {
        return barcode;
    }

    public List<Preco> getListaPreco() {
        return listaPreco;
    }

    public String getURL_Imagem() {
        return URL_Imagem;
    }

    public List<Avaliacao> getListaAvaliacao() {
        return listaAvaliacao;
    }

    public String getLoja() {
        return loja;
    }

    public String getPreco() {
        return preco;
    }
}
