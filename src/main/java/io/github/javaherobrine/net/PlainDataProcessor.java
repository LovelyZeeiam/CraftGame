package io.github.javaherobrine.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.github.javaherobrine.ioStream.IOUtils;

@Deprecated
public class PlainDataProcessor implements DataProcessor {
    public static final PlainDataProcessor DEFAULT_PROCESSOR = new PlainDataProcessor();
    public static final DataProcessor NO_PROCESS = new DataProcessor() {
        @Override
        public void write(OutputStream os, byte[] source) throws IOException {
            os.write(source);
        }

        @Override
        public byte[] read(InputStream is) throws IOException {
            return IOUtils.readNBytes(is, 1);
        }
    };
    private PlainDataProcessor() {
    }

    @Override
    public void write(OutputStream os, byte[] source) throws IOException {
        os.write(IOUtils.intToByte4(source.length));
        os.write(source);
    }

    @Override
    public byte[] read(InputStream is) throws IOException, InterruptedException {
        return IOUtils.readNBytes(is, IOUtils.byte4ToInt(IOUtils.readNBytes(is, 4), 0));
    }
}
