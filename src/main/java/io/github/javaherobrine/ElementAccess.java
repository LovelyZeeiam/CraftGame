package io.github.javaherobrine;

public interface ElementAccess<T, V> {
    void add(V v);

    void delete(T index);

    void replace(T index, V val);
}
