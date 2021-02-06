package xueli.gamengine.server;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Server {

    private final int port;
    private ServerSocket socket;

    public Server(int port) {
        this.port = port;

    }

    public void start() throws IOException {
        socket = new ServerSocket(port);
        

    }

}
