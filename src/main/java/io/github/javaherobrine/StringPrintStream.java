package io.github.javaherobrine;

import java.io.PrintStream;

public class StringPrintStream extends PrintStream {
    private PrintStream printer;
    private StringFormatter formatter;

    public StringPrintStream(PrintStream out, StringFormatter sf) {
        super(out);
        printer = out;
        formatter = sf;
    }

    public void print(String str) {
        printer.print(formatter.format(str));
    }
}
