package xueli.craftgame;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.message.Message;
import xueli.craftgame.net.message.MessageDefine;
import xueli.craftgame.net.server.Server;
import xueli.craftgame.world.World;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.GUITextView;
import xueli.gamengine.view.View;
import xueli.utils.Logger;

public class WorldLogic implements Runnable {

	private final CraftGame cg;
	public boolean running = false;

	private boolean isRemote;
	private Client client;
	private World world;

	// When isRemote = false, the following variable won't be null
	private Server server;

	public WorldLogic(CraftGame cg) {
		this.cg = cg;
		this.isRemote = false;

		this.world = new World();

		this.server = new Server(this);
		try {
			this.client = new Client(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public WorldLogic(CraftGame cg, String serverIp) {
		this.cg = cg;
		this.isRemote = true;

		this.world = new World();

		try {
			this.client = new Client(new URI("ws://" + serverIp), this);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		cg.queueRunningInMainThread.add(this::size);

		View loadingView = cg.getGuiResource().getGui("world_loading.json");
		GUIProgressBar progressBar = (GUIProgressBar) loadingView.widgets.get("loading_bar");

		if (!this.isRemote) {
			this.server.start();

		}

		boolean connectSuccess = false;
		String failedMessage = "";

		try {
			connectSuccess = this.client.connectBlocking(5000, TimeUnit.SECONDS);
		} catch (Exception e) {
			failedMessage += e.getMessage() + "\n";
		}

		if (!connectSuccess) {
			failedMessage += Logger.getLastLogString();

			View failedView = cg.getGuiResource().getGui("server_connect_failed.json");
			GUITextView textView = (GUITextView) failedView.widgets.get("message_details");
			textView.setText(failedMessage);

			if (isRemote) {
				try {
					this.server.stop();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

			}

			cg.queueRunningInMainThread.add(() -> cg.getViewManager().setGui("server_connect_failed.json"));

			return;

		}

		progressBar.setProgress(0.25f);

		client.send(Message.generateMessage(
				new Message(MessageDefine.PLAYER_CONNECT, CraftGame.INSTANCE_CRAFT_GAME.getPlayerStat().getName())));

	}

	public CraftGame getCg() {
		return cg;
	}

	public World getWorld() {
		return world;
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
		cg.inWorld = false;
		cg.getViewManager().setGui("world_saving.json");

		try {
			this.client.closeBlocking();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (!this.isRemote) {
			try {
				this.server.stop();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		}

		cg.getViewManager().setGui("main_menu.json");

	}

}
