package io.github.javaherobrine;

@FunctionalInterface
public interface StringFormatter {
    StringFormatter NON_FORMAT = (str) -> {
        return str;
    };

    String format(String str);
}
