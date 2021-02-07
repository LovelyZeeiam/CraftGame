package io.github.javaherobrine;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.util.Base64;

public final class URIProcessor {
    public static final Base64.Encoder ENCODER = Base64.getEncoder();
    public static final Desktop UTIL = Desktop.getDesktop();

    private URIProcessor() {
    }

    public static final boolean isURL(URI uri) {
        try {
            uri.toURL();
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static final void open(URI uri) throws IOException {
        UTIL.browse(uri);
    }

    public static final void mailto(String addr) throws IOException {
        try {
            UTIL.mail(URI.create("mailto:" + addr));
        } catch (UnsupportedOperationException e) {

        }
    }

    public static final void sendMailWithSMTP(String username, String password, String subject, String content, String from, String to) throws IOException {
        Socket soc = new Socket("smtp.qq.com", 25);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
        bw.write("HELO jaro");
        bw.newLine();
        bw.write("AUTH LOGIN");
        bw.newLine();
        bw.write(ENCODER.encodeToString(username.getBytes()));
        bw.newLine();
        bw.write(ENCODER.encodeToString(password.getBytes()));
        bw.newLine();
        bw.write("DATA");
        bw.newLine();
        bw.write("MAIL FROM:" + from);
        bw.newLine();
        bw.write("RCPT TO:" + to);
        bw.newLine();
        bw.write("SUBJECT:" + subject);
        bw.newLine();
        bw.newLine();
        bw.write(content);
        bw.newLine();
        bw.write(".");
        bw.newLine();
        bw.write("QUIT");
        bw.flush();
        soc.close();
    }
}
