package org.poo.cb;

public class Actiuni {
    private String nume;
    private int cantitate;

    public Actiuni(String nume, int cantitate) {
        this.nume = nume;
        this.cantitate = cantitate;
    }

    public String getNume() {
        return nume;
    }

    public int getCantitate() {
        return cantitate;
    }
}
