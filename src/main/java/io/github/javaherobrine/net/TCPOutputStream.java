package io.github.javaherobrine.net;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Deprecated
public class TCPOutputStream extends FilterOutputStream {
    public DataProcessor dataproc;

    public TCPOutputStream(OutputStream in) {
        this(in, PlainDataProcessor.DEFAULT_PROCESSOR);
    }

    public TCPOutputStream(OutputStream in, DataProcessor proc) {
        super(in);
        dataproc = proc;
    }

    public void writeData(byte[] bs) throws IOException, InterruptedException {
        dataproc.write(this, bs);
    }
}