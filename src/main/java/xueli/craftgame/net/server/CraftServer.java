package xueli.craftgame.net.server;

import java.util.Scanner;

public class CraftServer extends Server {

	public CraftServer() {
		super();
		
	}

	@Override
	public void onInit() {
		
		
	}

	@Override
	public void onUpdate() {
		
		
	}

	@Override
	public void onExit() {
		
		
	}

	public static void main(String[] args) {
		CraftServer server = new CraftServer();
		Thread thread = new Thread(server);
		thread.start();
		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			String input = scanner.nextLine();
			if("q".equals(input)) break;
			
		}
		scanner.close();
		server.closeServer();
		
	}

}
