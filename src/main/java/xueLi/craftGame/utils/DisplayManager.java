package xueLi.craftGame.utils;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

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

	public static Dimension screenSize;
	
	public static void create(int width, int height, Canvas canvas) {
		try {
			//这里是lwjgl自带的方法  设置屏幕的宽和高
			Display.setDisplayMode(new DisplayMode(width, height));
			//设置窗口是可以被更改大小的
			Display.setResizable(true);
			//这个应该是垂直同步之类的
			Display.setSwapInterval(1);
			Display.setVSyncEnabled(true);
			//这个是这个屏幕的附属 可以通过传入一个Canvas来达到投射到一个awt控件的功能
			Display.setParent(canvas);
			//没有设置控件（即传入null）就会创造一个新的窗口 这个方法是设置标题
			Display.setTitle("CraftGame - dev");
			//获取计算机屏幕宽和高
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			//窗口居中
			Display.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);

			//这里是设置OpenGL的版本
			ContextAttribs ca = new ContextAttribs(3, 2);
			//可以向前兼容
			ca.withForwardCompatible(true);
			//这个不知道嘤嘤嘤
			ca.withProfileCore(true);
			//将ContextAttribs传入lwjgl 然后创造一个窗口
			Display.create(new PixelFormat(), ca);
			//这个是让这个窗口变成当前的window
			Display.makeCurrent();

			//创建鼠标和键盘的监听
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		//这个地方是游戏循环的时候用到的
		isRunning = true;

		//设置OpenGL的渲染视窗
		GL11.glViewport(0, 0, width, height);

		//启动纹理支持
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		//启动深度测试 就是有一个前后的物体区分
		//如果没有这个东西的话就没有前后物体之分了
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_ONE);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);

		// The website said these are the way to anti-aliasing
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
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (Keyboard.getEventKeyState()) {
				keyboard[key] = true;
				key_pressed_frame.add(key);
			} else
				keyboard[Keyboard.getEventKey()] = false;
		}
	}

	public static boolean isRunning() {
		//这条语句是获取当前的时间戳
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
