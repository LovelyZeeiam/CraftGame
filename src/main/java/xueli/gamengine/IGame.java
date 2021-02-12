package xueli.gamengine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import xueli.gamengine.resource.*;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.TimerQueue;
import xueli.gamengine.utils.callbacks.*;
import xueli.gamengine.view.ViewManager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public abstract class IGame implements Runnable {

	static {
		try {
			Class.forName("org.lwjgl.system.Library");
			Class.forName("org.lwjgl.nanovg.LibNanoVG");
			Class.forName("org.lwjgl.stb.LibSTB");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Queue<Runnable> queueRunningInMainThread = new LinkedList<Runnable>();
	public TimerQueue timerQueue = new TimerQueue();
	
	protected LangManager langManager;
	protected String game_name;
	protected Options options;
	protected Display display;
	protected ShaderResource shaderResource;
	protected ViewManager viewManager;
	protected TextureManager textureManager;
	protected GuiResource guiResource;
	protected DataResource dataResource;
	protected AudioResource audioResource;
	private String resPath;

	public IGame(String resPath) {
		this.resPath = resPath;

	}

	protected void loadLang() {
		langManager = new LangManager(resPath);
		langManager.loadLang();
		langManager.setLang("zh-ch.lang");

		game_name = langManager.getStringFromLangMap("#game.name");

	}

	protected void loadOptions() {
		options = new Options(resPath);
		options.load();

	}

	protected void loadShader() {
		shaderResource = new ShaderResource(resPath);
		shaderResource.load();

	}

	protected void loadTexture() {
		viewManager = new ViewManager(display, options, shaderResource.get("gui"));
		textureManager = new TextureManager(resPath, viewManager);
		textureManager.load();

	}

	protected void loadGui() {
		guiResource = new GuiResource(resPath, textureManager);
		guiResource.loadGui("game_loading.json", langManager, false);
		viewManager.setResourceSource(guiResource);
		viewManager.setFont("Minecraft.ttf");

	}

	protected void loadData() {
		dataResource = new DataResource(resPath);

	}

	protected void loadAudio() {
		this.audioResource = new AudioResource(resPath);
		try {
			this.audioResource.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void createDisplay(int width, int height) {
		display = new Display();
		display.setDefaultWindowHints();
		display.setResizable(GLFW.GLFW_TRUE);
		display.create(width, height, game_name);
		display.setIcon(resPath);

		display.setSizedCallback(new DisplaySizedCallback() {
			@Override
			public void sized() {
				if (viewManager != null)
					viewManager.size();
				onSized();

			}
		});
		display.setCursorPosCallback(new CursorPosCallback() {
			@Override
			public void invoke() {
				onCursorPos(mouseDX, mouseDY);

			}
		});
		display.setMouseButtonCallback(new MouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				super.invoke(window, button, action, mods);
				if (viewManager != null)
					viewManager.mouseClicked(button, action, display.getMouseX(), display.getMouseY());

				if (action == GLFW.GLFW_RELEASE)
					onMouseButton(button);

			}
		});
		display.setKeyboardCallback(new KeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				super.invoke(window, key, scancode, action, mods);
				if (viewManager != null)
					viewManager.keyAction(key, action, mods);
				if (action == GLFW.GLFW_PRESS)
					onKeyboard(key);

			}
		});
		display.setCharCallback(new CharCallback() {
			@Override
			public void invoke(long window, int codepoint) {
				if (viewManager != null)
					viewManager.keyAction(codepoint);

			}
		});
		display.setScrollCallback(new MouseScrollCallback() {
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				if (viewManager != null)
					viewManager.mouseScroll((float) yoffset);
				onScroll((float) yoffset);

			}
		});

	}

	protected void showDisplay() {
		display.showWindow();

	}

	protected void initAll(int width, int height) {
		loadLang();
		loadOptions();
		createDisplay(width, height);
		loadShader();
		loadTexture();
		loadGui();
		loadAudio();

		// For Linux
		display.getSizedCallback().invoke(0, width, height);

	}

	public LangManager getLangManager() {
		return langManager;
	}

	public String getGame_name() {
		return game_name;
	}

	public Options getOptions() {
		return options;
	}

	public Display getDisplay() {
		return display;
	}

	public ShaderResource getShaderResource() {
		return shaderResource;
	}

	public ViewManager getViewManager() {
		return viewManager;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	public GuiResource getGuiResource() {
		return guiResource;
	}

	public DataResource getDataResource() {
		return dataResource;
	}

	public void runQueueList() {
		if (!queueRunningInMainThread.isEmpty())
			queueRunningInMainThread.poll().run();
		timerQueue.tick();

	}

	protected void releaseAll() {
		display.destroy();
		langManager.close();
		try {
			audioResource.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (options != null)
			options.close();
		if (shaderResource != null)
			shaderResource.close();
		if (textureManager != null)
			textureManager.close();
		if (guiResource != null)
			guiResource.close();

	}

	@Override
	public void run() {
		onCreate();
		while (display.running) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			onDrawFrame();
			display.update();
		}
		onExit();

	}

	protected abstract void onCreate();

	protected abstract void onSized();

	protected abstract void onCursorPos(double dx, double dy);

	protected abstract void onMouseButton(int button);

	protected abstract void onScroll(float scroll);

	protected abstract void onKeyboard(int key);

	protected abstract void onDrawFrame();

	protected abstract void onExit();

}
