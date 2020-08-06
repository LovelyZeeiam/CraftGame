package xueLi.gamengine.utils;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import xueLi.gamengine.utils.display.DisplaySizedCallback;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Display {

	public static Display currentDisplay;

	private Dimension screenSize;

	private long window;
	private String mainTitle;

	private DisplaySizedCallback sizedCallback;

	public boolean running = false;

	public boolean create(int width, int height, String title) {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			Logger.error("Sorry can't init GLFW~ xD");
			return false;
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		// 窗口可缩放
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		// OpenGL版本
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		// 采样次数
		// glfwWindowHint(GLFW_SAMPLES, 4);

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
		// GL11.glEnable(GL11.GL_POINT_SMOOTH);
		// GL11.glHint(GL11.GL_POINT_SMOOTH_HINT,GL11.GL_NICEST);

		return true;
	}

	public void setSizedCallback(DisplaySizedCallback callback) {
		glfwSetWindowSizeCallback(window, callback);
		this.sizedCallback = callback;
	}

	public void showWindow() {
		glfwShowWindow(window);
		this.running = true;

	}

	public void update() {
		Time.tick();

		glfwSwapBuffers(window);
		glfwPollEvents();

		if (glfwWindowShouldClose(window))
			running = false;

	}

	public void setSubtitle(String subtitle) {
		glfwSetWindowTitle(window, this.mainTitle + " - " + subtitle);
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

	public void destroy() {
		glfwDestroyWindow(window);
		glfwTerminate();

		glfwSetErrorCallback(null).free();

	}

}
