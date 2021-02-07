package io.github.javaherobrine.net;

import java.io.Serializable;
import java.util.HashSet;

public class Blacklist implements Serializable {
    HashSet<String> list;

    public Blacklist() {
        list = new HashSet<>();
    }

    public Blacklist(HashSet<String> init) {
        list = init;
    }

    public Blacklist(String... init) {
        this();
        for (int i = 0; i < init.length; i++) {
            list.add(init[i]);
        }
    }

    public void add(String name) {
        list.add(name);
    }

    public boolean isInBlacklist(String name) {
        return list.contains(name);
    }
}
