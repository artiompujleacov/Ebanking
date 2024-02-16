package org.poo.cb;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RecomandareActiuni {

    public RecomandareActiuni() {
    }

    public List<String> recomandaActiuni(String file) {
        List<String> recomandari = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String numeActiune = parts[0];
                    List<Double> valori = new ArrayList<>();

                    for (int i = 1; i < parts.length; i++) {
                        valori.add(Double.parseDouble(parts[i]));
                    }

                    double mediaScurta = calculMedieScurta(valori);
                    double mediaLunga = calculMedieLunga(valori);

                    if (mediaScurta > mediaLunga) {
                        recomandari.add(numeActiune);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recomandari;
    }

    private double calculMedieScurta(List<Double> valori) {
        int lungimeMedieScurta = 5;
        return valori.subList(valori.size() - lungimeMedieScurta, valori.size())
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

    private double calculMedieLunga(List<Double> valori) {
        int lungimeMedieLunga = 10;
        return valori.subList(valori.size() - lungimeMedieLunga, valori.size())
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }
}
