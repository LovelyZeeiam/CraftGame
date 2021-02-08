package xueli.gamengine.server;

import java.net.ServerSocket;
import java.util.ArrayList;

public abstract class Server implements Runnable {

    private final int port;
    private ServerSocket socket;

    private ArrayList<Client> clients = new ArrayList<>();
    
    private Thread serverAcceptThread = new Thread(() -> {

    });
    
    public Server(int port) {
        this.port = port;

    }

	@Override
	public void run() {

	}
	
	public void close() {

	}

	

}
