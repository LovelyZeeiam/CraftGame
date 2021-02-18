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
import xueli.gamengine.utils.vector.Vector;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.GUITextView;
import xueli.gamengine.view.View;
import xueli.utils.Logger;
import xueli.utils.Waiter;

public class WorldLogic implements Runnable {

	private final CraftGame cg;
	public boolean running = false;

	private boolean isRemote;
	private Client client;
	private World world;

	// When isRemote = false, the following variable won't be null
	private Server server;
	
	private Vector clientPlayerPos;

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

		// 第一步 连接服务器
		
		boolean connectSuccess = false;
		String failedMessage = "";
		
		if (!this.isRemote) {
			this.server.start();
			
			// 防止有些时候server没有启动 client就在开始连接抛出拒绝连接exception
			a: while(!this.server.isStarted()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(this.server.getException() != null || !this.server.isStarted()) {
						failedMessage += this.server.getException().getMessage();
					break a;
				}
				
			}
			
		}

		if(isRemote ? true : this.server.isStarted()) {
			try {
				connectSuccess = this.client.connectBlocking(5000, TimeUnit.SECONDS);
			} catch (Exception e) {
				failedMessage += e.getMessage() + "\n";
			}
		}

		if (!connectSuccess) {
			if(isRemote)
				failedMessage += Logger.getLastLogString();

			View failedView = cg.getGuiResource().getGui("server_connect_failed.json");
			GUITextView textView = (GUITextView) failedView.widgets.get("message_details");
			textView.setText(failedMessage);

			if (!isRemote) {
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

		// 第二步 生成出生点区块
		
		client.send(Message.generateMessage(
				new Message(MessageDefine.PLAYER_CONNECT, CraftGame.INSTANCE_CRAFT_GAME.getPlayerStat().getName())));
		
		synchronized (Waiter.waitObject) {
			try {
				Waiter.waitObject.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		progressBar.setProgress(0.5f);
		
		// 第三步 准备区块绘制
		
		

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

		if(this.client.isOpen()) {
			try {
				this.client.closeBlocking();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (!this.isRemote && this.server.isStarted()) {
			try {
				this.server.stop();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		}

		cg.getViewManager().setGui("main_menu.json");

	}

}
