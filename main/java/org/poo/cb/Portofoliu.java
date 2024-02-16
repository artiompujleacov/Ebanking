package org.poo.cb;
import java.util.ArrayList;
import java.util.List;

public class Portofoliu {
    private String valuta;
    private List<Cont> conturi;
    private List<Actiuni> actiuni;

    public Portofoliu(){
        this.conturi = new ArrayList<>();
        this.actiuni = new ArrayList<>();
    }
    public Portofoliu(String valuta) {
        this.valuta = valuta;
    }

    public void adaugaCont(Cont cont) {
        conturi.add(cont);
    }

    public void adaugaActiune(Actiuni actiune) {
        actiuni.add(actiune);
    }

    public String getValuta() {
        return valuta;
    }

    public List<Cont> getConturi() {
        return conturi;
    }

    public List<Actiuni> getActiuni() {
        return actiuni;
    }
}
