package io.github.javaherobrine.net;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.LinkedList;

import io.github.javaherobrine.ModLoader;
import io.github.javaherobrine.StringPrintStream;
import io.github.javaherobrine.ioStream.IOUtils;
import io.github.javaherobrine.net.sync.ClientSideSynchronizeImpl;

public class Server implements Closeable {
    private static final StringPrintStream STDOUT = new StringPrintStream(System.out, (str) -> {
        return "[Server]" + str;
    });
    public static Server thisServer;
    public LinkedList<Client> clients = new LinkedList<>();
    ServerSocket server;
    Thread hook = new Thread(() -> {
        try {
            server.close();
        } catch (IOException e) {
        }
    });

    {
        Runtime.getRuntime().addShutdownHook(hook);
    }

    private Server() {
    }

    public Server(ServerSocket server) {
        this.server = server;
    }

    protected static void nullServer(Client sucess) {
        thisServer = new Server();
        thisServer.clients.add(sucess.msg.id, sucess);
    }

    @Override
    public void close() throws IOException {
        Runtime.getRuntime().removeShutdownHook(hook);
        server.close();
    }

    public Client accept() throws IOException {
        Client c = new Client(server.accept(), false);
        BufferedReader br = new BufferedReader(new InputStreamReader(c.is, "UTF-8"));
        boolean accepted = false;
        while (!accepted) {
            TransmissionFormat format;
            try {
                format = TransmissionFormat.valueOf(br.readLine());
            } catch (Exception e) {
                c.os.write(IOUtils.intToByte4(1));
                continue;
            }
            if (format == TransmissionFormat.RECONNECT) {
                int id = Integer.parseInt(br.readLine());
                Client oldClient = clients.remove(id);
                c.msg = oldClient.msg;
                clients.add(id, c);
            }
            if (format == TransmissionFormat.FINISH) {
                c.os.write(IOUtils.intToByte4(-10));
                c.msg.connected = false;
                c.msg.format = null;
                c.msg.status = TransmissionStatus.CONTINUE;
                c.msg.id = -1;
                c.close();
                STDOUT.print("Client Transmission Format Not Support\r\n");
                break;
            } else {
                c.os.write(IOUtils.intToByte4(0));
                String[] cmods = br.readLine().split(",");
                Arrays.sort(cmods);
                String[] smods = ModLoader.loader.toString().split(",");
                Arrays.sort(smods);
                if (Arrays.equals(cmods, smods)) {
                    c.os.write(IOUtils.intToByte4(1));
                    clients.add(c);
                    c.msg.connected = true;
                    c.msg.format = format;
                    c.msg.status = TransmissionStatus.ACCEPTED;
                    c.os.write(IOUtils.intToByte4(clients.indexOf(c)));
                    c.msg.id = clients.indexOf(c);
                    c.msg.mods = cmods;
                    accepted = true;
                    STDOUT.print("A client connected\r\n");
                } else {
                    c.os.write(IOUtils.intToByte4(-1));
                }
            }
        }
        return c;
    }

    public ClientSideSynchronizeImpl.ServertSideSynchronizeImpl getImpl() throws IOException {
        return new ClientSideSynchronizeImpl(accept(), true).new ServertSideSynchronizeImpl();
    }
}