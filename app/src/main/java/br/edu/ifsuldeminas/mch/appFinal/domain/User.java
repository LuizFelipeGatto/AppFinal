package br.edu.ifsuldeminas.mch.appFinal.domain;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String nome;
    private String naturalidade;

    public User(Integer id, String nome, String naturalidade){
        this.id = id;
        this.nome = nome;
        this.naturalidade = naturalidade;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    @Override
    public String toString(){
        return String.format("%s\n%s", nome,
                naturalidade);
    }
}
