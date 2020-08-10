package xueLi.gamengine.utils;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import xueLi.gamengine.utils.callbacks.CursorPosCallback;
import xueLi.gamengine.utils.callbacks.DisplaySizedCallback;
import xueLi.gamengine.utils.callbacks.KeyCallback;
import xueLi.gamengine.utils.callbacks.MouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Display {

	public static Display currentDisplay;

	private Dimension screenSize;

	private long window;
	private String mainTitle;

	private DisplaySizedCallback sizedCallback;
	private CursorPosCallback cursorPosCallback;
	private MouseButtonCallback mouseButtonCallback;
	private KeyCallback keyCallback;

	public boolean running = false;

	public Display() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			Logger.error("Sorry can't init GLFW~ xD");
			return;
		}
	}

	public void setDefaultWindowHints() {
		glfwDefaultWindowHints();
	}

	public boolean create(int width, int height, String title) {
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		// OpenGL版本
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		// 采样次数
		glfwWindowHint(GLFW_SAMPLES, 4);

		// 创建窗口
		window = glfwCreateWindow(width, height, title, 0, 0);
		this.mainTitle = title;
		if (window == 0) {
			Logger.error("Sorry can't create window xD");
			return false;
		}

		// 获取计算机屏幕宽和高
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 窗口居中
		glfwSetWindowPos(window, (screenSize.width - width) / 2, (screenSize.height - height) / 2);
		// 窗口图标

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		glfwSwapInterval(1);

		currentDisplay = this;

		// 抗锯齿 多重采样
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		// 平滑线
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		// 启用点的抗锯齿
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		return true;
	}

	public void setResizable(int resizable) {
		glfwWindowHint(GLFW_RESIZABLE, resizable);
	}

	public void setSizedCallback(DisplaySizedCallback callback) {
		glfwSetWindowSizeCallback(window, callback);
		this.sizedCallback = callback;
	}

	public void setCursorPosCallback(CursorPosCallback callback) {
		glfwSetCursorPosCallback(window, callback);
		this.cursorPosCallback = callback;
	}

	public void setMouseButtonCallback(MouseButtonCallback callback) {
		glfwSetMouseButtonCallback(window, callback);
		this.mouseButtonCallback = callback;
	}

	public void setKeyboardCallback(KeyCallback keyCallback) {
		glfwSetKeyCallback(window, keyCallback);
		this.keyCallback = keyCallback;
	}

	public void showWindow() {
		glfwShowWindow(window);
		this.running = true;

	}

	public void update() {
		Time.tick();

		if (this.keyCallback != null)
			this.keyCallback.tick();

		glfwSwapBuffers(window);
		glfwPollEvents();

		if (glfwWindowShouldClose(window))
			running = false;

	}

	public void setSubtitle(String subtitle) {
		if (subtitle != null)
			glfwSetWindowTitle(window, this.mainTitle + " - " + subtitle);
		else
			glfwSetWindowTitle(window, this.mainTitle);
	}

	public int getWidth() {
		return sizedCallback.width;
	}

	public int getHeight() {
		return sizedCallback.height;
	}

	public float getRatio() {
		return sizedCallback.ratio;
	}

	public float getScale() {
		return sizedCallback.scale;
	}

	public int getMouseX() {
		return (int) cursorPosCallback.mouseX;
	}

	public int getMouseY() {
		return (int) cursorPosCallback.mouseY;
	}

	public boolean isMouseDown(int button) {
		return this.mouseButtonCallback.buttons[button];
	}

	public void destroy() {
		glfwDestroyWindow(window);
		glfwTerminate();

		glfwSetErrorCallback(null).free();

	}

}
