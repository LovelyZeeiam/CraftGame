package xueLi.craftGame.utils;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import xueLi.craftGame.world.Chunk;
import xueLi.craftGame.world.World;

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

			Display.create();
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

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);

		FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
		float[] fogColour = { 0.8f, 0.8f, 1.0f, 1.0f };
		fogColor.put(fogColour);
		fogColor.flip();

		GL11.glEnable(GL11.GL_FOG);
		GL11.glFog(GL11.GL_FOG_COLOR, fogColor);
		GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_DENSITY, 0.003f);
		GL11.glFogf(GL11.GL_FOG_START, World.chunkRenderDistance * Chunk.size * 0.56f);
		GL11.glFogf(GL11.GL_FOG_END, World.chunkRenderDistance * Chunk.size * 1.90f);
		GL11.glHint(GL11.GL_FOG_HINT, GL11.GL_DONT_CARE);

		//The website says these are the way to anti-aliasing
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
		
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
