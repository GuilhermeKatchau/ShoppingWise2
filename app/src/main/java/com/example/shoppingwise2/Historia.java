package com.example.shoppingwise2;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Historia {

    @SerializedName("id")  // Este campo pode ser omitido ao enviar para o backend
    private Integer id;

    @SerializedName("id_utilizador")
    private int idUtilizador;

    @SerializedName("id_produto")
    private int idProduto;

    @SerializedName("nome")
    private String nome;

    @SerializedName("imagem")
    private String imagem;

    @SerializedName("data")
    private String data;

    
    public Historia(int idUtilizador, int idProduto, String nome, String imagem) {
        this.idUtilizador = idUtilizador;
        this.idProduto = idProduto;
        this.nome = nome;
        this.imagem = imagem;
        this.data = new Date().toString();
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getNome() {
        return nome;
    }

    public String getImagem() {
        return imagem;
    }

    public String getData() {
        return data.replace("T", " ");
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setData(String data) {
        this.data = data;
    }
}
