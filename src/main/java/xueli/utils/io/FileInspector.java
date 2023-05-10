package xueli.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.fusesource.jansi.Ansi;

import xueli.utils.Strings;

public class FileInspector implements AutoCloseable {
    
    public static final int COLUME_BYTE_COUNT = 64;

    private final RandomAccessFile file;

    public FileInspector(File file) throws FileNotFoundException {
        this.file = new RandomAccessFile(file, "r");
    }

    public long getLength() throws IOException {
        return file.length();
    }

    public void inspect(long start, long end) throws IOException {
        long length = getLength();
        if(start > length) throw new IOException("Can't seek above file length: " + start);
        if(end < start) throw new IOException("end < start: " + end + " < " + start);
        start = Math.max(start, 0);
        end = Math.min(end, length);

        file.seek(start);

        Ansi a = Ansi.ansi();
        long pointer = (start / COLUME_BYTE_COUNT) * COLUME_BYTE_COUNT;
        file.seek(pointer);

        int bytePrintCounter = COLUME_BYTE_COUNT; // initial value
        byte[] temp = new byte[COLUME_BYTE_COUNT];

        while(pointer < end) {
            if(bytePrintCounter == COLUME_BYTE_COUNT) {
                a.a(" | ");
                for(int i = 0; i < COLUME_BYTE_COUNT; i++) {
                    if(Character.isISOControl(temp[i])) {
                        a.a(' ');
                    } else {
                        a.a((char) temp[i]);
                    }
                }

                String startHex = Long.toHexString(pointer);
                a.newline();
                a.a(String.format("%8s | ", startHex));
                
                bytePrintCounter = 0;
                file.read(temp);

            }

            if(pointer >= start && pointer < end) {
                String byteHex = Integer.toHexString(temp[bytePrintCounter]);
                byteHex = Strings.padLeft(byteHex, 2, '0');
                a.a(byteHex).a(" ");
                
                pointer++;
                bytePrintCounter++;

            }

            bytePrintCounter++;

        }

        System.out.println(a.toString());

    }
    
    @Override
    public void close() throws Exception {
        file.close();
    }

}
