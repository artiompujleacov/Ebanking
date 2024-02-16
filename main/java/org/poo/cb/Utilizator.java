package org.poo.cb;
import java.util.ArrayList;
import java.util.List;

public class Utilizator{
    private String email;
    private String nume;
    private String prenume;
    private String adresa;
    private Portofoliu portofoliu;
    private List<Utilizator> prieteni;

    public Utilizator(String email, String nume, String prenume, String adresa, Portofoliu portofoliu) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.portofoliu = portofoliu;
        this.prieteni = new ArrayList<>();
    }

    public void addFriend(Utilizator friend) {
        prieteni.add(friend);
    }

    public String getEmail() {
        return email;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getAdresa() {
        return adresa.replace("/", " ");
    }

    public Portofoliu getPortofoliu() {
        return portofoliu;
    }

    public List<Utilizator> getPrieteni() {
        return prieteni;
    }

    public static class UtilizatorBuilder{
        private String email;
        private String nume;
        private String prenume;
        private String adresa;
        private Portofoliu portofoliu;
        private List<Utilizator> prieteni;

        public UtilizatorBuilder(String email, String nume, String prenume, String adresa) {
            this.email = email;
            this.nume = nume;
            this.prenume = prenume;
            this.adresa = adresa;
            this.prieteni = new ArrayList<>();
            this.portofoliu = new Portofoliu();
        }

        public UtilizatorBuilder setPortofoliu(Portofoliu portofoliu) {
            this.portofoliu = portofoliu;
            return this;
        }

        public Utilizator build() {
            return new Utilizator(email, nume, prenume, adresa, portofoliu);
        }
    }
}

