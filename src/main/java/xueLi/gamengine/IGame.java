package xueLi.gamengine;

import java.util.LinkedList;
import java.util.Queue;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import marshmello.Happier;
import xueLi.gamengine.resource.GuiResource;
import xueLi.gamengine.resource.LangManager;
import xueLi.gamengine.resource.Options;
import xueLi.gamengine.resource.ShaderResource;
import xueLi.gamengine.resource.TextureManager;
import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.callbacks.CursorPosCallback;
import xueLi.gamengine.utils.callbacks.DisplaySizedCallback;
import xueLi.gamengine.utils.callbacks.KeyCallback;
import xueLi.gamengine.utils.callbacks.MouseButtonCallback;
import xueLi.gamengine.view.ViewManager;

@Happier
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

	private String resPath;

	public IGame(String resPath) {
		this.resPath = resPath;

	}

	protected LangManager langManager;
	protected String game_name;
	protected Options options;
	protected Display display;
	protected ShaderResource shaderResource;
	protected ViewManager viewManager;
	protected TextureManager textureManager;
	protected GuiResource guiResource;

	public Queue<Runnable> queueRunningInMainThread = new LinkedList<Runnable>();

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
		textureManager.preload();
		textureManager.load();

	}

	protected void loadGui() {
		guiResource = new GuiResource(resPath, textureManager);
		guiResource.loadGui("game_loading.json", langManager);
		viewManager.setResourceSource(guiResource);
		viewManager.setFont("Minecraft.ttf");

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
				if (action == GLFW.GLFW_RELEASE)
					viewManager.mouseClicked(button);
				onMouseButton(button);

			}
		});
		display.setKeyboardCallback(new KeyCallback() {
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

	public void runQueueList() {
		if (!queueRunningInMainThread.isEmpty())
			queueRunningInMainThread.poll().run();

	}

	protected void releaseAll() {
		display.destroy();
		langManager.close();
		options.close();
		shaderResource.close();
		textureManager.close();
		textureManager.release();
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

	protected abstract void onDrawFrame();

	protected abstract void onExit();

}
