package xueli.game2.display;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Display {

	private long window;
	private boolean running = false;
	private String mainTitle;

	private int width, height;
	private float display_scale;

	private float cursor_x, cursor_y;
	private float cursor_dx = 0, cursor_dy = 0;

	private double wheel_delta;

	private boolean mouseGrabbed = false;

	private ArrayList<WindowSizeListener> sizeCallbacks = new ArrayList<>();
	private GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback() {
		@Override
		public void invoke(long window, int w, int h) {
			if (w != 0 || h != 0) {
				width = w;
				height = h;
				display_scale = getScale(w, h);

				sizeCallbacks.forEach(c -> c.onSize(w, h));

			}
		}
	};

	private GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
		@Override
		public void invoke(long window, double xpos, double ypos) {
			cursor_dx = (float) (xpos - cursor_x);
			cursor_dy = (float) (ypos - cursor_y);

			cursor_x = (float) xpos;
			cursor_y = (float) ypos;

		}
	};

	private boolean[] mouse_buttons = new boolean[8];

	private GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
		@Override
		public void invoke(long window, int button, int action, int mods) {
			if (button >= 0 && action == GLFW_RELEASE) {
				mouse_buttons[button] = true;
			}

		}
	};

	private boolean[] keys = new boolean[65536];
	private boolean[] keyboard_keys = new boolean[65536];
	private ArrayList<Integer> last_press_keys = new ArrayList<>();
	private ArrayList<KeyInputListener> keyCallbacks = new ArrayList<>();
	private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (key >= 0 && action == GLFW_PRESS) {
				keyboard_keys[key] = true;
				last_press_keys.add(key);
			}
			if (key >= 0)
				keys[key] = action != GLFW_RELEASE;

			keyCallbacks.forEach(c -> c.onInput(key, scancode, action, mods));

		}
	};

	private GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			wheel_delta = yoffset;
		}
	};

	public Display(int width, int height, String title) {
		this.width = width;
		this.height = height;
		display_scale = getScale(width, height);
		this.mainTitle = title;

	}

	private float getScale(int width, int height) {
		return Math.min(width, height) / 400.0f * 0.6f;
	}

	public void create() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new RuntimeException("Can't init GLFW!");
		}

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		window = glfwCreateWindow(width, height, mainTitle, 0, 0);

		if (window == 0) {
			throw new RuntimeException("Can't create window!");
		}

		// get width and height
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// center the window
		glfwSetWindowPos(window, (screenSize.width - width) / 2, (screenSize.height - height) / 2);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		glfwSwapInterval(1);

		glfwSetWindowSizeCallback(window, windowSizeCallback);
		glfwSetCursorPosCallback(window, cursorPosCallback);
		glfwSetMouseButtonCallback(window, mouseButtonCallback);
		glfwSetKeyCallback(window, keyCallback);
		glfwSetScrollCallback(window, scrollCallback);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		this.running = true;

	}

	public void addKeyListener(KeyInputListener callback) {
		keyCallbacks.add(callback);
	}

	public void removeKeyListener(KeyInputListener callback) {
		keyCallbacks.remove(callback);
	}

	public void show() {
		glfwShowWindow(window);

	}

	public void hide() {
		glfwHideWindow(window);

	}

	private void callbackTick() {
		for (int i = 0; i < 8; i++)
			mouse_buttons[i] = false;

		for (Integer r : last_press_keys)
			keyboard_keys[r] = false;
		last_press_keys.clear();

		wheel_delta = 0;
		cursor_dx = 0;
		cursor_dy = 0;

	}

	public void update() {
		callbackTick();

		glfwSwapBuffers(window);
		glfwPollEvents();

		if (glfwWindowShouldClose(window))
			running = false;

	}

	public void setMouseGrabbed(boolean mouseGrabbed) {
		if(this.mouseGrabbed == mouseGrabbed)
			return;

		glfwSetInputMode(window, GLFW_CURSOR, mouseGrabbed ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
		this.mouseGrabbed = mouseGrabbed;

	}

	public void release() {
		this.running = false;
		glfwDestroyWindow(window);
		glfwTerminate();

	}

	public double getWheelDelta() {
		return wheel_delta;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getCursorX() {
		return cursor_x;
	}

	public float getCursorY() {
		return cursor_y;
	}

	public float getCursor_dx() {
		return cursor_dx;
	}

	public float getCursor_dy() {
		return cursor_dy;
	}

	public float getDisplayScale() {
		return display_scale;
	}

	public boolean isKeyDown(int key) {
		return keys[key];
	}

	public boolean isKeyDownOnce(int key) {
		return keyboard_keys[key];
	}

	public boolean isMouseDown(int mouse) {
		return glfwGetMouseButton(window, mouse) == GLFW_PRESS;
	}

	public boolean isMouseDownOnce(int mouse) {
		return mouse_buttons[mouse];
	}

	public boolean isMouseGrabbed() {
		return mouseGrabbed;
	}
	
	public List<Integer> getPressedKeyList() {
		return last_press_keys;
	}

}
