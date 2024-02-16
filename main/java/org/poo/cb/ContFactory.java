package org.poo.cb;

public class ContFactory implements Factory<Cont> {
    @Override
    public Cont create(String... args) {
        return new Cont(args[0]);
    }
}
