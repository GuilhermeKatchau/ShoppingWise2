package com.example.shoppingwise2;

public class Pesquisa {
    private String data;
    private String comparacao;
    private String produto;
        Pesquisa(String data, String produto, String comparacao){
            this.comparacao = comparacao;
            this.data = data;
            this.produto = produto;
        }
    public String getData() { return data; }
    public String getProduto() { return produto; }
    public String getComparacao() { return comparacao;}
}
