package xueli.gamengine.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import io.github.javaherobrine.net.Client;
import io.github.javaherobrine.net.event.EventContent;

public abstract class Server implements Runnable {

    private final int port;
    private ServerSocket socket;

    private io.github.javaherobrine.net.Server server;

    private ArrayList<Client> clients = new ArrayList<>();
    
    private Thread serverAcceptThread = new Thread(() -> {
    	while(true) {
    		try {
				Client client = server.accept();
				if(client != null) {
					onAccept(client, clients.size());
					clients.add(client);

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    });
    
    public Server(int port) {
        this.port = port;

    }

	@Override
	public void run() {
		try {
			this.socket = new ServerSocket(port);
			this.server = new io.github.javaherobrine.net.Server(this.socket);

			serverAcceptThread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(this.socket != null) {
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void close() {
		if(this.socket != null) {
			serverAcceptThread.interrupt();
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public abstract void onAccept(Client client, int id);
	public abstract void onReceiveMessage(EventContent event);
	

}
