package com.example.shoppingwise2;

public class Utilizador {
    private String nome;
    private String email;
    private int pnum;

    public Utilizador(String nome, String email, int pnum) {
        this.nome = nome;
        this.email = email;
        this.pnum = pnum;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getPnum() {
        return pnum;
    }
}
