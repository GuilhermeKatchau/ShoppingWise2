package com.example.shoppingwise2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class Produto {
    @SerializedName("id_produto")
    private Integer id_produto;

    @SerializedName("nome")
    private String nome;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("url_imagem")
    private String url_imagem;

    // Lista de preços por loja (1 produto → muitas lojas)
    private List<PrecoLoja> precosLojas;

    // Construtor para a API (sem ID)
    public Produto(String nome, String barcode, String url_imagem) {
        this.nome = nome;
        this.barcode = barcode;
        this.url_imagem = url_imagem;
        this.precosLojas = new ArrayList<>();
    }

    // Adiciona um preço/loja à lista
    public void addPrecoLoja(String loja, String preco, String imagemLoja) {
        precosLojas.add(new PrecoLoja(loja, preco, imagemLoja));
    }

    public int getId_produto() {
        return id_produto;
    }



    public String getNome() {
        return nome;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getUrl_imagem() {
        return url_imagem;
    }

    public List<PrecoLoja> getPrecosLojas() {
        return precosLojas;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setUrl_imagem(String url_imagem) {
        this.url_imagem = url_imagem;
    }

    public void setPrecosLojas(List<PrecoLoja> precosLojas) {
        this.precosLojas = precosLojas;
    }
}
