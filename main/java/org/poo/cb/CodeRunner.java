package org.poo.cb;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CodeRunner {
    private static volatile CodeRunner instance;

    private CodeRunner() {
    }

    public static CodeRunner getInstance() {
        if (instance == null) {
            synchronized (CodeRunner.class) {
                if (instance == null) {
                    instance = new CodeRunner();
                }
            }
        }
        return instance;
    }

    void runCode(String[] args) {
        if(args == null) {
            System.out.println("Running Main");
            return;
        }
        List<Utilizator> userList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/"+args[2]))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String comanda = parts[0] + " " + parts[1];
                switch(comanda){
                    case "CREATE USER":
                        String email = parts[2];
                        for (Utilizator u : userList) {
                            if (u.getEmail().equals(email)) {
                                System.out.println("User with " + email + " already exists");
                                return;
                            }
                        }
                        String nume = parts[3];
                        String prenume = parts[4];
                        String adresa ="";
                        for(int i = 5; i < parts.length; i++) {
                            adresa = adresa + "/" + parts[i];
                            adresa = adresa.trim();
                        }
                        Utilizator utilizator = new Utilizator.UtilizatorBuilder(email, nume, prenume, adresa).build();
                        userList.add(utilizator);
                        break;
                    case "ADD FRIEND":
                        String mail1 = parts[2];
                        String mail2 = parts[3];
                        Utilizator u1 = null;
                        Utilizator u2 = null;
                        for(Utilizator u : userList){
                            if(u.getEmail().equals(mail1)){
                                u1 = u;
                            }
                            if(u.getEmail().equals(mail2)){
                                u2 = u;
                            }
                        }
                        if(u1 == null){
                            System.out.println("User with " + mail1 + " doesn't exist");
                        }
                        else if(u2 == null){
                            System.out.println("User with " + mail2 + " doesn't exist");
                        }
                        else {
                            u1.addFriend(u2);
                            u2.addFriend(u1);
                        }
                        break;
                    case "LIST USER":
                        String mail= parts[2];
                        boolean found=false;
                        for(Utilizator u : userList){
                            if(u.getEmail().equals(mail)){
                                found = true;
                                System.out.print("{");
                                System.out.print("\"email\":\"" + u.getEmail() + "\",");
                                System.out.print("\"firstname\":\"" + u.getNume() + "\",");
                                System.out.print("\"lastname\":\"" + u.getPrenume() + "\",");
                                System.out.print("\"address\":\"" + u.getAdresa() + "\",");
                                System.out.print("\"friends\":[");
                                for(Utilizator f : u.getPrieteni()){
                                    if(u.getPrieteni().indexOf(f) == u.getPrieteni().size() - 1){
                                        System.out.print("\"" + f.getEmail() + "\"");
                                    }
                                    else
                                        System.out.print("\"" + f.getEmail() + "\",");
                                }
                                System.out.print("]");
                                System.out.println("}");
                            }
                        }
                        if(!found){
                            System.out.println("User with "+ mail + " doesn't exist");
                        }
                        break;
                    case "ADD ACCOUNT":
                        String email_cont= parts[2];
                        String tipValuta = parts[3];
                        ContFactory contFactory = new ContFactory();
                        Cont cont = contFactory.create(tipValuta);
                        for(Utilizator u : userList){
                            if(u.getEmail().equals(email_cont)){
                                u.getPortofoliu().adaugaCont(cont);
                                break;
                            }
                        }
                        break;
                    case "ADD MONEY":
                        String email_user = parts[2];
                        String valuta = parts[3];
                        double suma = Double.parseDouble(parts[4]);
                        for(Utilizator u : userList){
                            if(u.getEmail().equals(email_user)){
                                OperatieFinanciaraStrategy strategy = new AdaugaBaniStrategy();
                                strategy.executaOperatie(u, suma,valuta,"",null);
                                break;
                            }
                        }
                        break;
                    case "EXCHANGE MONEY":
                        String email_user1 = parts[2];
                        String valuta1 = parts[3];
                        String valuta2 = parts[4];
                        double suma1 = Double.parseDouble(parts[5]);
                        for(Utilizator u : userList){
                            if(u.getEmail().equals(email_user1)){
                                OperatieFinanciaraStrategy strategy = new SchimbaBaniStrategy();
                                strategy.executaOperatie(u, suma1,valuta1,valuta2,null);
                                break;
                            }
                        }
                        break;
                    case "TRANSFER MONEY":
                        String email_u1 = parts[2];
                        String email_u2 = parts[3];
                        String valuta3 = parts[4];
                        double suma2 = Double.parseDouble(parts[5]);
                        for(Utilizator utilizator1 : userList){
                            for(Utilizator utilizator2 : userList) {
                                if (utilizator1.getEmail().equals(email_u1) && utilizator2.getEmail().equals(email_u2)) {
                                    if (utilizator1.getPrieteni().contains(utilizator2)) {
                                        OperatieFinanciaraStrategy strategy = new TransferaBaniStrategy();
                                        strategy.executaOperatie(utilizator1, suma2, valuta3, "", utilizator2);
                                        break;
                                    } else {
                                        System.out.println("You are not allowed to trannsfer money to " + email_u2);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case "BUY STOCKS":
                        String email_usr = parts[2];
                        String nume_actiune = parts[3];
                        int cantitate = Integer.parseInt(parts[4]);
                        for(Utilizator u : userList){
                            if(u.getEmail().equals(email_usr)){
                                OperatieFinanciaraStrategy strategy = new CumparaActiuniStrategy();
                                strategy.executaOperatie(u, cantitate,nume_actiune,args[1],null);
                                break;
                            }
                        }
                        break;
                    case "LIST PORTFOLIO":
                        String email_usr1 = parts[2];
                        for(Utilizator u : userList){
                            if(u.getEmail().equals(email_usr1)){
                                System.out.print("{");
                                System.out.print("\"stocks\":[");
                                for(Actiuni a : u.getPortofoliu().getActiuni()){
                                    if(u.getPortofoliu().getActiuni().indexOf(a) == u.getPortofoliu().getActiuni().size() - 1){
                                        System.out.print("{\"stockname\":\"" + a.getNume() + "\",\"amount\":" + a.getCantitate() + "}");
                                    }
                                    else
                                        System.out.print("{\"stockname\":\"" + a.getNume() + "\",\"amount\":" + a.getCantitate() + "},");
                                }
                                System.out.print("],");
                                System.out.print("\"accounts\":[");
                                for(Cont c : u.getPortofoliu().getConturi()){
                                    DecimalFormat df = new DecimalFormat("#.00");
                                    System.out.print("{");
                                    System.out.print("\"currencyname\":\"" + c.getTipValuta() + "\",");
                                    if(c.getSuma()==0){
                                        System.out.print("\"amount\":\"" + "0.00" + "\"");
                                    }
                                    else{
                                        System.out.print("\"amount\":" + "\"" + df.format(c.getSuma()) + "\"");
                                    }
                                    if(u.getPortofoliu().getConturi().indexOf(c) == u.getPortofoliu().getConturi().size() - 1){
                                        System.out.print("}");
                                    }
                                    else
                                        System.out.print("},");
                                }
                                System.out.print("]");
                                System.out.println("}");
                            }
                        }
                        break;
                    case "RECOMMEND STOCKS":
                        RecomandareActiuni recomandareActiuni = new RecomandareActiuni();
                        List<String> recomandari = recomandareActiuni.recomandaActiuni(args[1]);
                        System.out.print("{\"stockstobuy\":[");
                        for(String s:recomandari){
                            if(recomandari.indexOf(s) == recomandari.size() - 1){
                                System.out.print("\"" + s + "\"");
                            }
                            else
                                System.out.print("\"" + s + "\",");
                        }
                        System.out.println("]}");
                        break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
