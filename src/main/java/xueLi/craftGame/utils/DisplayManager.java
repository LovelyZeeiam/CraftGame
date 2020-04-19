package xueLi.craftGame.utils;

import java.awt.Canvas;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import xueLi.craftGame.world.Chunk;
import xueLi.craftGame.world.World;

public class DisplayManager {

	public static boolean isDebug = true;

	private static boolean isRunning = false;
	private static int fps_cap = 60;

	public static int d_width, d_height;

	public static long currentTime;
	public static long deltaTime;

	public static void create(int width, int height, Canvas canvas) {
		try {
			//Display.setDisplayMode(new DisplayMode(width, height));
			//Display.setResizable(true);
			Display.setSwapInterval(1);
			Display.setVSyncEnabled(true);
			Display.setParent(canvas);
			//Display.setTitle("CraftGame");

			ContextAttribs ca = new ContextAttribs(2,1);
			ca.withForwardCompatible(true);
			Display.create(new PixelFormat(), ca);
			Display.makeCurrent();

			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		isRunning = true;

		GL11.glViewport(0, 0, width, height);
		
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

		//The website said these are the way to anti-aliasing
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
	
	private static boolean[] keyboard = new boolean[256];
	private static ArrayList<Integer> key_pressed_frame = new ArrayList<Integer>();

	public static boolean isKeyDownOnce(int i) {
		return key_pressed_frame.contains(i);
	}
	
	public static boolean isKeyDown(int i) {
		return Keyboard.isKeyDown(i);
	}

	public static boolean isMouseDown(int i) {
		return Mouse.isButtonDown(i);
	}
	
	public static void keyTest() {
		key_pressed_frame.clear();
		while(Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (Keyboard.getEventKeyState()) {
				keyboard[key] = true;
				key_pressed_frame.add(key);
			}
			else 
				keyboard[Keyboard.getEventKey()] = false;
		}
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
