package com.example.shoppingwise2;

public class Utilizador {

    private int id;
    private String nome;
    private String passwd;
    private String email;
    private int pnum;

    public Utilizador(String nome, String passwd, String email, int pnum) {
        this.nome = nome;
        this.passwd = passwd;
        this.email = email;
        this.pnum = pnum;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getEmail() {
        return email;
    }

    public int getPnum() {
        return pnum;
    }
}
