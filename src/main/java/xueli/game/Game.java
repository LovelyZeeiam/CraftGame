package xueli.game;

import java.util.LinkedList;
import java.util.Queue;

import org.lwjgl.opengl.GL11;

import xueli.game.renderer.RendererManager;
import xueli.game2.display.Display;
import xueli.utils.exception.CrashReport;
import xueli.utils.logger.MyLogger;

@Deprecated
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
		rendererManager = new RendererManager();

		onCreate();
		display.show();

		onSize((int) getWidth(), (int) getHeight());

		while (display.isRunning()) {
			onTick();
			rendererManager.render();
//			GLHelper.checkGLError("[Renderer]");
			display.update();

			MyLogger.getInstance().pushState("MainThreadQueue");
			if (!queueInMainThread.isEmpty()) {
				queueInMainThread.poll().run();
			}
			MyLogger.getInstance().popState();

		}

		// display.hide();
		display.release();

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

	public void announceCrash(String state, Throwable throwable) {
		queueInMainThread.add(() -> {
			display.release();
			new CrashReport(state, throwable).showCrashReport();
		});
	}

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
