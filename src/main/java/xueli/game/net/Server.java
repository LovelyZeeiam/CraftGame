package xueli.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import xueli.game.event.Event;

public abstract class Server {

	private DatagramSocket socket;

	public Server(int port) {
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		// Just recieve all the time
		while (true) {
			byte[] data = new byte[1048576];
			DatagramPacket pkg = new DatagramPacket(data, data.length);
			try {
				socket.receive(pkg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Event event = EventProcessor.decodeEvent(pkg.getData(), 0, pkg.getLength());
			onRecieveEvent(event, pkg);

		}

	}

	public void close() {
		this.socket.close();

	}

	public abstract void tick();

	public abstract void onRecieveEvent(Event event, DatagramPacket packet);

}
