package com.example.shoppingwise2;

public class Avaliacao {
    private int id;
    private int numero; //Classificação
    private String comentario;
    private String fonte;

    public Avaliacao(int id, int numero, String comentario, String fonte) {
        this.id = id;
        this.numero = numero;
        this.comentario = comentario;
        this.fonte = fonte;
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public String getComentario() {
        return comentario;
    }

    public String getFonte() {
        return fonte;
    }
}
