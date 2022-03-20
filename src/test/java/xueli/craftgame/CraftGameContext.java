package xueli.craftgame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import xueli.craftgame.block.Blocks;
import xueli.craftgame.client.LocalTicker;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.renderer.HUDRenderer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.server.PlayerInfo;
import xueli.craftgame.world.World;
import xueli.game.Game;
import xueli.game.player.FirstPersonCamera;

public class CraftGameContext extends Game implements Runnable {
	
	private PlayerInfo playerInfo = new PlayerInfo("LoveliZeeiam");

	// Game Controller Eventbus
	private ExecutorService eventBusExecutor = Executors
			.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("Context-EventBus-pool-%d").build());
	private AsyncEventBus eventBus = new AsyncEventBus(eventBusExecutor);

	private World world;
	// All the events must be sent to here first
	private LocalTicker ticker;
	
	private WorldRenderer worldRenderer;
	private HUDRenderer hudRenderer;
	
	private LocalPlayer player;

	public CraftGameContext() {
		super(800, 600, "Minecraft PE");
		
	}

	@Override
	public void onCreate() {
		Blocks.initCall();

		this.ticker = new LocalTicker(this);
		this.world = new World(this);
		this.player = new LocalPlayer(new FirstPersonCamera(25, 10, 25), this);
		this.worldRenderer = new WorldRenderer(player, this);
		this.hudRenderer = new HUDRenderer(this);
		
		eventBus.register(this.worldRenderer);
		eventBus.register(this.world);
		
		this.worldRenderer.init();
		this.hudRenderer.init();

		this.ticker.start();

		getDisplay().setMouseGrabbed(true);

	}

	@Override
	public void onTick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		
		this.world.tick();
		this.worldRenderer.render();
		this.player.tick();
		
		this.hudRenderer.render();
		
		if (getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE)) {
			getDisplay().setMouseGrabbed(!getDisplay().isMouseGrabbed());
		}

	}

	@Override
	public void onSize(int width, int height) {
		super.onSize(width, height);

		this.worldRenderer.onSize(width, height);
		
	}

	@Override
	public void onRelease() {
		this.ticker.stop();

		this.worldRenderer.release();
		this.hudRenderer.release();
		
		try {
			if (!eventBusExecutor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
				eventBusExecutor.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		world.release();

	}

	public void submitEventToMainTicker(Object eventObject) {
		ticker.submitEvent(eventObject);
	}

	public void submitEvent(Object eventObject) {
		eventBus.post(eventObject);
	}

	public World getWorld() {
		return world;
	}

	public LocalTicker getTicker() {
		return ticker;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}
	
	public LocalPlayer getPlayer() {
		return player;
	}
	
	public WorldRenderer getWorldRenderer() {
		return worldRenderer;
	}

	public static void main(String[] args) {
		new CraftGameContext().run();

	}

}
