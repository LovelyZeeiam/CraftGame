package xueli.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import xueli.game.event.Event;

public abstract class Client {

	private DatagramSocket socket;

	private InetAddress address;
	private int port;

	public Client(String address, int port) {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		try {
			this.address = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = port;

	}

	public void send(Event event) {
		send(EventProcessor.encodeEvent(event));

	}

	private void send(byte[] bytes) {
		DatagramPacket outputPacket = new DatagramPacket(bytes, bytes.length, address, port);
		try {
			socket.send(outputPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		// Just recieve all the time
		while (true) {
			byte[] data = new byte[2048];
			DatagramPacket pkg = new DatagramPacket(data, data.length);
			try {
				socket.receive(pkg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Event event = EventProcessor.decodeEvent(data);
			onRecieveEvent(event, pkg);

		}

	}

	public void close() {
		this.socket.close();

	}

	public abstract void tick();

	public abstract void onRecieveEvent(Event event, DatagramPacket packet);

}
