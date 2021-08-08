package xueli.game;

import org.lwjgl.opengl.GL11;
import xueli.game.display.Display;
import xueli.game.renderer.RendererManager;
import xueli.game.utils.GLHelper;
import xueli.game.utils.Time;
import xueli.utils.logger.MyLogger;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Game implements Runnable {

	public static Game INSTANCE_GAME;
	public static final String DEFAULT_RES_DIRECTORY_STRING = "./res/";
	public static final String DEFAULT_WORKING_DIRECTORY_STRING = "./.cg/";

	private final Display display;

	private final Queue<Runnable> queueInMainThread = new LinkedList<>();

	protected RendererManager rendererManager;

	public Game(int width, int height, String windowTitle) {
		INSTANCE_GAME = this;

		this.display = new Display(width, height, windowTitle);

	}

	@Override
	public void run() {
		display.create();
		Time.tick();
		rendererManager = new RendererManager();

		onCreate();
		display.show();

		onSize((int) getWidth(), (int) getHeight());
		Time.tick();

		while (display.isRunning()) {
			onTick();
			rendererManager.render();
			GLHelper.checkGLError("[Renderer]");
			display.update();
			Time.tick();

			MyLogger.getInstance().pushState("MainThreadQueue");
			if (!queueInMainThread.isEmpty()) {
				queueInMainThread.poll().run();
			}
			MyLogger.getInstance().popState();

		}

		display.hide();
		rendererManager.release();
		onRelease();

	}

	public abstract void onCreate();

	public abstract void onTick();

	protected void defaultViewport() {
		GL11.glViewport(0, 0, display.getWidth(), display.getHeight());

	}

	public void onSize(int width, int height) {
		defaultViewport();
		rendererManager.size(width, height);

	}

	public abstract void onRelease();

	public Display getDisplay() {
		return display;
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

	public RendererManager getRendererManager() {
		return rendererManager;
	}

	public void addTaskForMainThread(Runnable run) {
		this.queueInMainThread.add(run);
	}

}
