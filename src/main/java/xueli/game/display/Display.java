package xueli.game.display;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import xueli.game.utils.GLHelper;
import xueli.utils.io.Log;

public class Display {

	private int width, height;

	private long window;
	private boolean running = false;
	private String mainTitle;

	public Display(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.mainTitle = title;

	}

	public void create() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			Log.logger.severe("Can't init GLFW!");
			return;
		}

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		// OpenGL版本
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		window = glfwCreateWindow(width, height, mainTitle, 0, 0);

		if (window == 0) {
			Log.logger.severe("Can't create window!");
			return;
		}

		// 获取计算机屏幕宽和高
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 窗口居中
		glfwSetWindowPos(window, (screenSize.width - width) / 2, (screenSize.height - height) / 2);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		glfwSwapInterval(1);

		Log.logger.fine("Window created!");

		GLHelper.printDeviceInfo();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void show() {
		glfwShowWindow(window);
		this.running = true;

	}

	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();

		if (glfwWindowShouldClose(window))
			running = false;

	}

	public boolean isRunning() {
		return running;
	}

}
