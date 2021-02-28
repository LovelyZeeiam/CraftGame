package xueli.game;

import java.util.LinkedList;
import java.util.Queue;

import org.lwjgl.opengl.GL11;

import xueli.game.display.Display;
import xueli.game.utils.GLHelper;
import xueli.game.utils.Time;
import xueli.game.utils.renderer.RendererManager;

public abstract class Game implements Runnable {

	public static Game INSTANCE_GAME;

	private Display display;
	protected final String workingDirectory;

	private Queue<Runnable> queueInMainThread = new LinkedList<>();

	private RendererManager manager = new RendererManager();

	public Game(int width, int height, String windowTitle, String workingDirectory) {
		INSTANCE_GAME = this;

		this.display = new Display(width, height, windowTitle);
		this.workingDirectory = workingDirectory;

	}

	@Override
	public void run() {
		display.create();
		Time.tick();

		oncreate();
		display.show();

		while (display.isRunning()) {
			ontick();
			manager.render();
			GLHelper.checkGLError("[Renderer]");
			display.update();
			Time.tick();

			if (!queueInMainThread.isEmpty()) {
				queueInMainThread.poll().run();
			}

		}

		onrelease();

	}

	public abstract void oncreate();

	public abstract void ontick();

	public void onSize(int width, int height) {
		GL11.glViewport(0, 0, width, height);
		manager.size(width, height);

	}

	public abstract void onrelease();

	public RendererManager getManager() {
		return manager;
	}

	public float getWidth() {
		return display.getWidth();
	}

	public float getHeight() {
		return display.getHeight();
	}

	public float getDisplayScale() {
		return display.getDisplayScale();
	}

	public float getCursorX() {
		return display.getCursorX();
	}

	public float getCursorY() {
		return display.getCursorY();
	}

	public void addTaskForMainThread(Runnable run) {
		this.queueInMainThread.add(run);
	}

}
