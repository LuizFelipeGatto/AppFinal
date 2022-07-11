package br.edu.ifsuldeminas.mch.appFinal.domain;

import java.io.Serializable;

public class Cidade implements Serializable {

    private int id;
    private String name;
    private String uf;
    private String dateAAAAMMDD;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDateAAAAMMDD() {
        return dateAAAAMMDD;
    }

    public void setDateAAAAMMDD(String dateAAAAMMDD) {
        this.dateAAAAMMDD = dateAAAAMMDD;
    }
}
