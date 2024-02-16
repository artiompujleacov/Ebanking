package org.poo.cb;

public class ActiuneFactory implements Factory<Actiuni> {
    @Override
    public Actiuni create(String... args) {
        int cantitate = Integer.parseInt(args[1]);
        return new Actiuni(args[0], cantitate);
    }
}
