package io.github.javaherobrine.ioStream;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Writer;

import io.github.javaherobrine.JavaScript;

public class JSONOutputStream implements ObjectOutput {
    private Writer dest;

    public JSONOutputStream(Writer dest) {
        this.dest = dest;
    }

    public void writeBoolean(boolean v) throws IOException {
    }

    public void writeByte(int v) throws IOException {
    }

    public void writeShort(int v) throws IOException {
    }

    public void writeChar(int v) throws IOException {
    }

    public void writeInt(int v) throws IOException {
    }

    public void writeLong(long v) throws IOException {
    }

    public void writeFloat(float v) throws IOException {
    }

    public void writeDouble(double v) throws IOException {
    }

    public void writeBytes(String s) throws IOException {
    }

    public void writeChars(String s) throws IOException {
    }

    public void writeUTF(String s) throws IOException {
    }

    public void writeObject(Object obj) throws IOException {
        dest.write(JavaScript.json(obj));
    }

    public void write(int b) throws IOException {
    }

    public void write(byte[] b) throws IOException {
    }

    public void write(byte[] b, int off, int len) throws IOException {
    }

    public void flush() throws IOException {
    }

    public void close() throws IOException {
        dest.close();
    }
}
