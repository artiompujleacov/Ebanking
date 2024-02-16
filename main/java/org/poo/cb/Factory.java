package org.poo.cb;

public interface Factory<T> {
    T create(String... args);
}
