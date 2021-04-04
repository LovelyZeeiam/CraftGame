package xueli.craftgame.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Logger;

import xueli.craftgame.event.EventPlayerConnected;
import xueli.craftgame.event.EventWorld;
import xueli.game.event.Event;
import xueli.game.net.Server;
import xueli.game.player.PlayerStat;
import xueli.utils.io.Files;
import xueli.utils.io.Log;

public class CraftServer extends Server {

	private static final String icons_path = "./temp/icons/";

	public CraftServer(int port) {
		super(port);

		Logger.getGlobal().info("Starting server...");

	}

	@Override
	public void onRecieveEvent(Event event, DatagramPacket pkg) {
		if (event instanceof EventWorld) {

		} else {
			if (event instanceof EventPlayerConnected) {
				EventPlayerConnected epc = (EventPlayerConnected) event;
				PlayerStat stat = new PlayerStat(epc.getName(), icons_path + epc.getName() + ".icon");

				try {
					Files.fileOutput(stat.getIconPath(), epc.getIcon());
				} catch (IOException e) {
					e.printStackTrace();
				}

				Log.logger.info("[Server] " + stat.getName() + " joined the game");

			}

		}

	}

	@Override
	public void tick() {

	}

	public static void main(String[] args) {
		CraftServer server = new CraftServer(8000);
		server.run();
		server.close();

	}

}
