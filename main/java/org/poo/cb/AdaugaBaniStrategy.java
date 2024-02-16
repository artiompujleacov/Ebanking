package org.poo.cb;

public class AdaugaBaniStrategy implements OperatieFinanciaraStrategy{
    @Override
    public void executaOperatie(Utilizator u, double suma, String tipValuta, String tipValuta2,Utilizator u2) {
        for(Cont c : u.getPortofoliu().getConturi()){
            if(c.getTipValuta().equals(tipValuta)){
                c.setSuma(c.getSuma() + suma);
            }
        }
    }
}
