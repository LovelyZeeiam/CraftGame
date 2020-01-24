package xueLi.craftGame.utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	public static boolean isDebug = true;

	private static boolean isRunning = false;
	private static int fps_cap = 60;

	public static int d_width, d_height;

	public static long currentTime;
	public static long deltaTime;

	public static void create(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setResizable(true);
			Display.setSwapInterval(1);
			Display.setVSyncEnabled(true);
			Display.setTitle("CraftGame");

			ContextAttribs attribs = new ContextAttribs(3, 2);
			attribs.withForwardCompatible(true);
			attribs.withProfileCore(true);

			Display.create(new PixelFormat(), attribs);
			Display.makeCurrent();

			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		isRunning = true;

		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_ONE);

		d_width = width;
		d_height = height;

	}

	public static boolean tickResize() {
		if (Display.wasResized()) {
			d_width = Display.getWidth();
			d_height = Display.getHeight();
			GL11.glViewport(0, 0, d_width, d_height);

			return true;
		}
		return false;
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static void update() {
		if (Display.isCloseRequested())
			isRunning = false;
		Display.sync(fps_cap);
		Display.update();

		deltaTime = getCurrentTime() - currentTime;
	}

	public static boolean isKeyDown(int i) {
		return Keyboard.isKeyDown(i);
	}

	public static boolean isMouseDown(int i) {
		return Mouse.isButtonDown(i);
	}

	public static boolean isKeyDown() {
		return Keyboard.getEventKeyState();
	}

	public static boolean isRunning() {
		currentTime = getCurrentTime();
		return isRunning;
	}

	public static void postDestroyMessage() {
		isRunning = false;
	}

	public static void destroy() {
		isRunning = false;
		Display.destroy();
	}

}
