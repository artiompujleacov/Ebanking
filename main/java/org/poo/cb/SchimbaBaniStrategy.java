package org.poo.cb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SchimbaBaniStrategy implements OperatieFinanciaraStrategy {
    public void executaOperatie(Utilizator u, double suma, String tipValuta1, String tipValuta2,Utilizator u2) {
        Cont cont1 = null;
        Cont cont2 = null;
        for(Cont c : u.getPortofoliu().getConturi()){
            if(c.getTipValuta().equals(tipValuta1)){
                cont1 = c;
            }
            if(c.getTipValuta().equals(tipValuta2)){
                cont2 = c;
            }
        }
        if(cont1 == null){
            System.out.println("User doesn't have a " + tipValuta1 + " account");
            return;
        }
        if(cont2 == null){
            System.out.println("User doesn't have a " + tipValuta2 + " account");
            return;
        }
        if(cont1.getSuma() < suma){
            System.out.println("Insufficient amount in account " + tipValuta1 + " for exchange");
            return;
        }
        double sumaSchimbata = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/common/exchangeRates.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[0].equals(tipValuta2)) {
                    double cursValutar = Double.parseDouble(parts[tipValuta2ToIndex(tipValuta1)]);
                    sumaSchimbata = suma * cursValutar;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(sumaSchimbata > cont1.getSuma()/2){
            sumaSchimbata = sumaSchimbata+ (sumaSchimbata*0.01);
        }
        cont1.setSuma(cont1.getSuma() - sumaSchimbata);
        cont2.setSuma(cont2.getSuma() + suma);
    }

    private int tipValuta2ToIndex(String tipValuta2) {
        switch (tipValuta2) {
            case "EUR": return 1;
            case "GBP": return 2;
            case "JPY": return 3;
            case "CAD": return 4;
            case "USD": return 5;
        }
        return -1;
    }
}
