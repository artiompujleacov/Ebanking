package org.poo.cb;

public class Cont{

    private String tipValuta;
    private double suma;

    public Cont(String tipValuta) {
        this.tipValuta = tipValuta;
        this.suma = 0;
    }

    public String getTipValuta() {
        return tipValuta;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }
}
