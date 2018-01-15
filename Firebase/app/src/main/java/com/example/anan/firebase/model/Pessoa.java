package com.example.anan.firebase.model;

/**
 * Created by anan on 14/01/18.
 */

public class Pessoa {
    private String uid, nome, email;

    public Pessoa() {
    }

    public Pessoa(String uid, String nome, String email) {
        this.uid = uid;
        this.nome = nome;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nome;
    }
}
