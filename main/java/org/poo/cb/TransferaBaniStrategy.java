package org.poo.cb;

public class TransferaBaniStrategy implements OperatieFinanciaraStrategy{
    public void executaOperatie(Utilizator u, double suma, String tipValuta1, String tipValuta2,Utilizator u2) {
        for(Cont c : u.getPortofoliu().getConturi()){
            if(c.getTipValuta().equals(tipValuta1)){
                if(c.getSuma() < suma){
                    System.out.println("Insufficient amount in account " + c.getTipValuta()+ " for transfer");
                }
                else{
                c.setSuma(c.getSuma() - suma);
                }
            }
        }
        for(Cont c : u2.getPortofoliu().getConturi()){
            if(c.getTipValuta().equals(tipValuta1)){
                c.setSuma(c.getSuma() + suma);
            }
        }
    }
}
