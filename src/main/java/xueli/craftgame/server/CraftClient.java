package xueli.craftgame.server;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;

import xueli.craftgame.event.EventPlayerConnected;
import xueli.game.event.Event;
import xueli.game.net.Client;
import xueli.game.player.PlayerStat;
import xueli.utils.io.Files;

public class CraftClient extends Client {

	private PlayerStat stat;

	public CraftClient(String address, int port, PlayerStat playerStat) {
		super(address, port);
		this.stat = playerStat;

		try {
			send(new EventPlayerConnected(stat.getName(), Files.readAllByte(new File(stat.getIconPath()))));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onRecieveEvent(Event event, DatagramPacket pkg) {

	}

	@Override
	public void tick() {

	}

	public static void main(String[] args) {
		CraftClient client = new CraftClient("127.0.0.1", 8000, new PlayerStat());
		client.run();
		client.close();

	}

}
