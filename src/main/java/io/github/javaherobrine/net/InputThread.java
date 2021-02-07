package io.github.javaherobrine.net;

import java.io.Closeable;
import java.io.IOException;

@Deprecated
public class InputThread extends Thread implements Closeable, AutoCloseable {
    TCPInputStream in;
    boolean keep = true;
    boolean live = true;
    private byte[] now = null;

    public InputThread(TCPInputStream in) {
        this.in = in;
    }

    @Override
    public void close() throws IOException {
        in.close();
        live = false;
    }

    @Override
    public void run() {
        while (live) {
            if (keep) {
                try {
                    sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                }
            }
            try {
                synchronized (now) {
                    now = in.readData();
                }
            } catch (IOException | InterruptedException ee) {
            }
        }
    }

    public void ready() {
        interrupt();
    }

    public synchronized byte[] get() {
        synchronized (now) {
            return now;
        }
    }

    public synchronized void keepTransmitting() {
        keep = false;
        ready();
    }

    public synchronized void blockTransmitting() {
        keep = true;
    }
}
