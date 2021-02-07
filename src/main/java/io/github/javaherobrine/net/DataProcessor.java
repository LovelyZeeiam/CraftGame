package io.github.javaherobrine.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Deprecated
public interface DataProcessor {
    void write(OutputStream os, byte[] source) throws IOException, InterruptedException;

    byte[] read(InputStream is) throws IOException, InterruptedException;
}
