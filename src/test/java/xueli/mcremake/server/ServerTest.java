package xueli.mcremake.server;

import xueli.mcremake.classic.server.CraftGameServer;

public class ServerTest {
	
	public static void main(String[] args) {
		CraftGameServer server = new CraftGameServer(8000);
		server.run();
		
	}
	
}
