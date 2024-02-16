package org.poo.cb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CumparaActiuniStrategy implements OperatieFinanciaraStrategy{
    @Override
    public void executaOperatie(Utilizator u, double suma, String tipValuta1, String tipValuta2,Utilizator u2){
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + tipValuta2))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[0].equals(tipValuta1)) {
                    double ultimaValoare = Double.parseDouble(parts[parts.length - 1]);
                    double valoareTotala = ultimaValoare * suma;
                    for (Cont c : u.getPortofoliu().getConturi()) {
                        if (c.getTipValuta().equals("USD")) {
                            if (c.getSuma() >= valoareTotala) {
                                c.setSuma(c.getSuma() - valoareTotala);
                                ActiuneFactory actiuneFactory = new ActiuneFactory();
                                int sumaInt = (int) suma;
                                String suma1 = Integer.toString(sumaInt);
                                Actiuni actiuni = actiuneFactory.create(tipValuta1, suma1);
                                u.getPortofoliu().adaugaActiune(actiuni);
                                break;
                            } else {
                                System.out.println("Insufficient amount in account for buying stock");
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
