package xueLi.craftGame.utils;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import xueLi.craftGame.Constants;
import xueLi.craftGame.events.EventManager;
import xueLi.craftGame.events.KeyEvent;
import xueLi.craftGame.events.MouseButtonEvent;

public class Display {

	private static boolean isRunning = false;
	// private static int fps_cap = 60;

	public static int d_width, d_height;

	public static long currentTime;
	public static long deltaTime;

	private static boolean[] keys = new boolean[65536];
	private static boolean[] keysOnce = new boolean[65536];
	private static ArrayList<Integer> keyPressed = new ArrayList<Integer>();
	private static GLFWKeyCallback keycb = new GLFWKeyCallback() {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if(key == -1) {
				System.out.println("What is this key? -1");
			}
			
			keys[key] = action != GLFW.GLFW_RELEASE;

			keysOnce[key] = action == GLFW.GLFW_PRESS;
			keyPressed.add(key);
			
			if(!mouseGrabbed)
				EventManager.addKeyEvent(new KeyEvent(key, scancode, action));
				
		}
	};

	public static interface SizedCallback {
		public void sized(int width, int height);
	}

	private static SizedCallback szcbb;

	public static void setSizedCallback(SizedCallback cb) {
		szcbb = cb;
	}

	private static GLFWWindowSizeCallback sizecb = new GLFWWindowSizeCallback() {
		@Override
		public void invoke(long window, int width, int height) {
			d_width = width;
			d_height = height;
			GL11.glViewport(0, 0, d_width, d_height);

			if (szcbb != null)
				szcbb.sized(width, height);
		}
	};

	private static double lastTimeMouseX = 0, lastTimeMouseY = 0;
	public static double mouseDX = 0, mouseDY = 0;
	public static double mouseX = 0,mouseY = 0;
	private static boolean ShouldnotProcessMouseMoveEvent = true;
	private static GLFWCursorPosCallback cpcb = new GLFWCursorPosCallback() {
		@Override
		public void invoke(long window, double xpos, double ypos) {
			if (ShouldnotProcessMouseMoveEvent) {
				ShouldnotProcessMouseMoveEvent = false;
				return;
			}
			mouseDX = xpos - lastTimeMouseX;
			mouseDY = lastTimeMouseY - ypos;

			lastTimeMouseX = xpos;
			lastTimeMouseY = ypos;
			
			mouseX = xpos;
			mouseY = ypos;
			

		}
	};
	
	private static GLFWMouseButtonCallback mbcb = new GLFWMouseButtonCallback() {
		@Override
		public void invoke(long window, int button, int action, int mods) {
			EventManager.addMouseButtonEvent(new MouseButtonEvent(mouseX,mouseY,button, action));
			
		}
	};

	public static Dimension screenSize;

	private static long window;

	public static boolean create(int width, int height) {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!GLFW.glfwInit()) {
			System.err.println("Sorry the glfw can't init xD");
			return false;
		}

		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		// 窗口可缩放
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		// OpenGL版本
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
		
		// 创建窗口
		window = GLFW.glfwCreateWindow(width, height, Constants.GAME_NAME + (Constants.IS_DEBUG ? " - alpha" : ""), 0, 0);
		if (window == 0) {
			System.err.println("Sorry can't create window xD");
			return false;
		}

		// 获取计算机屏幕宽和高
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 窗口居中
		GLFW.glfwSetWindowPos(window, (screenSize.width - width) / 2, (screenSize.height - height) / 2);

		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();

		GLFW.glfwSetKeyCallback(window, keycb);
		GLFW.glfwSetWindowSizeCallback(window, sizecb);
		GLFW.glfwSetCursorPosCallback(window, cpcb);
		GLFW.glfwSetMouseButtonCallback(window, mbcb);

		GLFW.glfwShowWindow(window);

		GLFW.glfwSwapInterval(1);
		System.out.println(GL11.glGetString(GL11.GL_VERSION));

		GLHelper.printGLError("Window Creator");

		// 这个地方是游戏循环的时候用到的
		isRunning = true;

		// 设置OpenGL的渲染视窗
		GL11.glViewport(0, 0, width, height);
		GLHelper.printGLError("Init - Viewport");

		// 启动纹理支持
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GLHelper.printGLError("Init - Texture Support");

		// 启动深度测试 就是有一个前后的物体区分
		// 如果没有这个东西的话就没有前后物体之分了
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_ONE);
		GLHelper.printGLError("Init - Depth Test");

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GLHelper.printGLError("Init - Cull Face");

		// The website said these are the way to anti-aliasing
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
		GLHelper.printGLError("Init - Anti-aliasing");

		d_width = width;
		d_height = height;

		return true;
	}

	public static boolean mouseGrabbed = false;

	public static void grabMouse() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR,
				mouseGrabbed ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
		mouseGrabbed = !mouseGrabbed;
		ShouldnotProcessMouseMoveEvent = true;
	}

	private static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public static void update() {
		if (GLFW.glfwWindowShouldClose(window))
			isRunning = false;

		mouseDX = mouseDY = 0;

		for (int keyID : keyPressed)
			keysOnce[keyID] = false;

		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();

		deltaTime = getCurrentTime() - currentTime;
	}

	public static boolean isKeyDownOnce(int i) {
		return keysOnce[i];
	}

	public static boolean isKeyDown(int i) {
		return keys[i];
	}

	public static boolean isMouseDown(int i) {
		return GLFW.glfwGetMouseButton(window, i) == 1;
	}

	public static boolean isRunning() {
		// 这条语句是获取当前的时间戳
		currentTime = getCurrentTime();
		return isRunning;
	}
	
	public static void setSubtitie(String subtitle) {
		GLFW.glfwSetWindowTitle(window, Constants.GAME_NAME + (Constants.IS_DEBUG ? " - alpha" : "") + subtitle);
	}

	public static void postDestroyMessage() {
		isRunning = false;
	}

	public static void destroy() {
		isRunning = false;

		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);

		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();

	}

}
