package xueli.craftgame;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import xueli.craftgame.client.Client;
import xueli.craftgame.server.Server;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.GUITextView;
import xueli.gamengine.view.View;
import xueli.utils.Logger;

public class WorldLogic implements Runnable {

	private final CraftGame cg;
	public boolean running = false;
	
	private boolean isRemote;
	private Client client;
	
	// When isRemote = false, the following variable won't be null
	private Server server;

	public WorldLogic(CraftGame cg) {
		this.cg = cg;
		this.isRemote = false;
		
		this.server = new Server();
		try {
			this.client = new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public WorldLogic(CraftGame cg, String serverIp) {
		this.cg = cg;
		try {
			this.client = new Client(new URI("ws://" + serverIp));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		cg.queueRunningInMainThread.add(this::size);
		
		View loadingView = cg.getGuiResource().getGui("world_loading.json");
		GUIProgressBar progressBar = (GUIProgressBar) loadingView.widgets.get("loading_bar");
		
		if(!this.isRemote) {
			this.server.start();
			
		}
		
		boolean connectSuccess = false;
		String failedMessage = "";
		
		try {
			connectSuccess = this.client.connectBlocking(5000, TimeUnit.SECONDS);
		} catch (Exception e) {
			failedMessage += e.getMessage() + "\n";
		}
		
		if(!connectSuccess) {
			failedMessage += Logger.getLastLogString();
			
			View failedView = cg.getGuiResource().getGui("server_connect_failed.json");
			GUITextView textView = (GUITextView) failedView.widgets.get("message_details");
			textView.setText(failedMessage);
			
			cg.queueRunningInMainThread.add(() -> cg.getViewManager().setGui("server_connect_failed.json"));
			
			return;
			
		}
		
		progressBar.setProgress(0.25f);
		
		
		
	}

	public CraftGame getCg() {
		return cg;
	}
	
	public void size() {
		
	}

	public void mouseMove(double dx, double dy) {
		
	}

	public void onMouseClick(int button) {
		
	}

	public void onKeyboard(int key) {
		
	}

	public void onMouseScroll(float scroll) {
		
	}

	public void draw() {
		
	}

	public void delete() {
		
	}
	
}
