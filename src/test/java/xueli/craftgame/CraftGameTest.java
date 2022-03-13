package xueli.craftgame;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.lwjgl.opengl.GL11;

import com.google.common.eventbus.AsyncEventBus;

import xueli.craftgame.block.Blocks;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.server.PlayerInfo;
import xueli.craftgame.world.World;
import xueli.game.Game;
import xueli.game.player.FirstPersonCamera;

public class CraftGameTest extends Game implements Runnable {

	private static final PlayerInfo playerInfo = new PlayerInfo("LoveliZeeiam");

	private ExecutorService eventBusExecutor = Executors.newSingleThreadExecutor();
	private AsyncEventBus eventBus = new AsyncEventBus(eventBusExecutor);

	private World world;
	private WorldRenderer worldRenderer;
	private FirstPersonCamera camera;

	public CraftGameTest() {
		super(800, 600, "Minecraft PE");

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);

	}

	@Override
	public void onCreate() {
		Blocks.initCall();

		this.world = new World();
		this.camera = new FirstPersonCamera(0, 6, 0);
		this.worldRenderer = new WorldRenderer(this);

		eventBus.register(this.worldRenderer);
		eventBus.register(this.world);
		eventBus.register(this.world.getProvider());

		this.worldRenderer.init();

	}

	@Override
	public void onTick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		this.camera.tick();
		this.world.tick();

		this.worldRenderer.render();

	}

	@Override
	public void onSize(int width, int height) {
		super.onSize(width, height);

		this.worldRenderer.onSize(width, height);

	}

	@Override
	public void onRelease() {
		this.worldRenderer.release();

		try {
			eventBusExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		world.release();

	}

	public void submitEvent(Object eventObject) {
		eventBus.post(eventObject);
	}

	public World getWorld() {
		return world;
	}

	public static void main(String[] args) {
		new CraftGameTest().run();

	}

}
