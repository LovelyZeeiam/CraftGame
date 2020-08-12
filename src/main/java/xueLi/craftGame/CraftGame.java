package xueLi.craftGame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import xueLi.gamengine.resource.GuiResource;
import xueLi.gamengine.resource.LangManager;
import xueLi.gamengine.resource.Options;
import xueLi.gamengine.resource.ShaderResource;
import xueLi.gamengine.resource.TextureManager;
import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.callbacks.CursorPosCallback;
import xueLi.gamengine.utils.callbacks.DisplaySizedCallback;
import xueLi.gamengine.utils.callbacks.MouseButtonCallback;
import xueLi.gamengine.view.View;
import xueLi.gamengine.view.ViewManager;
import xueLi.gamengine.view.WorldRenderer;

public class CraftGame implements Runnable {

	private static int width = 1200, height = 680;

	public CraftGame() {

	}

	static LangManager langManager;
	static String game_name;
	static Options options;
	static Display display;
	static ShaderResource shaderResource;
	static ViewManager viewManager;
	static TextureManager textureManager;
	static GuiResource guiResource;

	static WorldRenderer worldRenderer;

	// 由于View的改变只能在主线程中完成才不会报错 所以,,,
	static View viewThatNeedToChange = null;

	@Override
	public void run() {
		try {
			Class.forName("org.lwjgl.system.Library");
			Class.forName("org.lwjgl.nanovg.LibNanoVG");
			Class.forName("org.lwjgl.stb.LibSTB");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		langManager = new LangManager("res/");
		langManager.loadLang();
		langManager.setLang("zh-ch.lang");

		game_name = langManager.getStringFromLangMap("#game.name");

		options = new Options("res/");
		options.load();

		// 先加载加载界面需要的资源 再加载:
		// 1. 加载加载界面需要的texture
		// 2. 加载加载界面的GUI
		// 3. 加载其它资源

		display = new Display();
		display.setDefaultWindowHints();
		display.setResizable(GLFW.GLFW_TRUE);
		display.create(width, height, game_name);
		display.setIcon("res/");

		shaderResource = new ShaderResource("res/");
		// 加载着色器
		shaderResource.load();

		viewManager = new ViewManager(display, options, shaderResource.get("gui"));
		textureManager = new TextureManager("res/", viewManager);
		textureManager.preload();

		// 回调
		display.setSizedCallback(new DisplaySizedCallback() {
			@Override
			public void sized() {
				viewManager.size();

			}
		});
		display.setCursorPosCallback(new CursorPosCallback() {
			@Override
			public void invoke() {
				System.out.println(this.mouseDX + ", " + this.mouseDY);
			}
		});
		display.setMouseButtonCallback(new MouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				super.invoke(window, button, action, mods);
				if (action == GLFW.GLFW_RELEASE)
					viewManager.mouseClicked(button);

			}
		});

		guiResource = new GuiResource("res/", textureManager);
		guiResource.loadGui("game_loading.json", langManager);

		viewManager.setResourceSource(guiResource);
		viewManager.setFont("Minecraft.ttf");

		// 加载材质
		textureManager.load();

		GameLogicThread gameLogicThread = new GameLogicThread();
		Thread thread = new Thread(gameLogicThread);

		// World Renderer初始化
		worldRenderer = new WorldRenderer();

		display.showWindow();

		thread.start();

		while (display.running) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

			// 绘制GUI
			viewManager.draw();

			if (viewThatNeedToChange != null) {
				viewManager.setGui(viewThatNeedToChange);
				viewThatNeedToChange = null;
			}

			display.update();
		}

		// TODO: 结束游戏时先销毁其它资源 最后再销毁结束游戏时的资源
		// 1. 销毁其它资源
		// 2. 销毁销毁界面的GUI和textures

		textureManager.close();
		options.close();
		langManager.close();
		shaderResource.close();

		display.destroy();

		if (gameLogicThread.sleeping)
			System.exit(0);

		if (gameLogicThread.waitingForLove) {
			synchronized (gameLogicThread.waiter) {
				gameLogicThread.waiter.notify();
			}
		}

	}

}
