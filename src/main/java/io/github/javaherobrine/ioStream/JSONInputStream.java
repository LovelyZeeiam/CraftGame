package io.github.javaherobrine.ioStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.Reader;
import java.io.StringWriter;

import javax.script.ScriptException;

import io.github.javaherobrine.JavaScript;

public class JSONInputStream implements ObjectInput {
    private BufferedReader source;

    public JSONInputStream(Reader source) {
        this.source = new BufferedReader(source);
    }

    @Override
    public Object readObject() throws IOException {
        StringWriter sw = new StringWriter();
        String str = source.readLine();
        if (str.trim().isEmpty()) {
            return readObject();
        }
        if (str.indexOf('}') == str.length() - 1) {
            try {
                return JavaScript.parse(str);
            } catch (ScriptException e) {
                throw new IOException(e);
            }
        }
        sw.write(str + "\n");
        while (!(str = source.readLine()).equals("}")) {
            sw.write(str);
        }
        sw.write("}");
        try {
            return JavaScript.parse(sw.toString());
        } catch (ScriptException e) {
            throw new IOException(e);
        }
    }

    public void readFully(byte[] b) throws IOException {
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
    }

    public int skipBytes(int n) throws IOException {
        return 0;
    }

    public boolean readBoolean() throws IOException {
        return false;
    }

    public byte readByte() {
        return 0;
    }

    public int readUnsignedByte() throws IOException {
        return 0;
    }

    public short readShort() throws IOException {
        return 0;
    }

    public int readUnsignedShort() throws IOException {
        return 0;
    }

    public char readChar() throws IOException {
        return 0;
    }

    public int readInt() throws IOException {
        return 0;
    }

    public long readLong() throws IOException {
        return 0;
    }

    public float readFloat() throws IOException {
        return 0;
    }

    public double readDouble() throws IOException {
        return 0;
    }

    public String readLine() throws IOException {
        return null;
    }

    public String readUTF() throws IOException {
        return null;
    }

    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] b) throws IOException {
        return 0;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return 0;
    }

    public long skip(long n) throws IOException {
        return 0;
    }

    public int available() throws IOException {
        return 0;
    }

    public void close() throws IOException {
        source.close();
    }
}
