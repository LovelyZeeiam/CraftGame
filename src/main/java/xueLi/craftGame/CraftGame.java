package xueLi.craftGame;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import xueLi.gamengine.gui.GUI;
import xueLi.gamengine.gui.GUIImageView;
import xueLi.gamengine.gui.GUIManager;
import xueLi.gamengine.gui.GUIProgressBar;
import xueLi.gamengine.gui.GUITextView;
import xueLi.gamengine.resource.GuiResource;
import xueLi.gamengine.resource.LangManager;
import xueLi.gamengine.resource.Options;
import xueLi.gamengine.resource.ShaderResource;
import xueLi.gamengine.resource.TextureManager;
import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.callbacks.CursorPosCallback;
import xueLi.gamengine.utils.callbacks.DisplaySizedCallback;

public class CraftGame implements Runnable {

	private static int width = 1200, height = 680;

	public CraftGame() {

	}

	@Override
	public void run() {
		try {
			Class.forName("org.lwjgl.system.Library");
			Class.forName("org.lwjgl.nanovg.LibNanoVG");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		LangManager langManager = new LangManager("res/");
		langManager.loadLang();
		langManager.setLang("zh-ch.lang");

		String game_name = langManager.getStringFromLangMap("#game.name");

		Options options = new Options("res/");
		options.load();

		// 先加载加载界面需要的资源 再加载:
		// 1. 加载加载界面需要的texture
		// 2. 加载加载界面的GUI
		// 3. 加载其它资源

		Display display = new Display();
		display.create(width, height, game_name);

		ShaderResource shaderResource = new ShaderResource("res/");
		// 加载着色器
		shaderResource.load();

		GUIManager guiManager = new GUIManager(display, options, shaderResource.get("gui"));
		TextureManager textureManager = new TextureManager("res/", guiManager);
		textureManager.preload();

		// 回调
		display.setSizedCallback(new DisplaySizedCallback() {
			@Override
			public void sized() {
				guiManager.size();

			}
		});
		display.setCursorPosCallback(new CursorPosCallback() {
			@Override
			public void invoke() {
				System.out.println(this.mouseDX + ", " + this.mouseDY);
			}
		});

		GuiResource guiResource = new GuiResource("res/", textureManager);
		guiResource.loadGui("game_loading.json", langManager);

		guiManager.setResourceSource(guiResource);

		GUI loading_gui = guiManager.setFadeinGui("game_loading.json");

		GUIImageView loading_imageView = (GUIImageView) loading_gui.widgets.get("loading_splash");
		GUIProgressBar loading_ProgressBar = (GUIProgressBar) loading_gui.widgets.get("loading_progress_bar");
		GUITextView loading_TextView = (GUITextView) loading_gui.widgets.get("loading_message");

		// 加载材质
		textureManager.load(loading_TextView, loading_ProgressBar, 0.0f, 0.25f);

		Thread gameLogicThread = new Thread(() -> {
			/* 资源加载 */
			
			String loading_messageString = loading_TextView.getText();

			// 设置加载动画
			loading_imageView.setAnimation("loading");
			// 加载GUI
			guiResource.loadGui(langManager, loading_TextView, loading_ProgressBar, 0.25f, 1.00f);
			
			loading_TextView.setText(loading_messageString);
			
			// 加载options

			// 加载方块

			// 加载物品

			// 等待直到进度条到底
			loading_TextView.setText("Loading...");
			loading_ProgressBar.waitUtilProgressFull();
			loading_TextView.setText("Loaded successfully~");

			// 换界面!
			guiManager.setFadeinGui("main_menu.json");

			/* Game Logic */

			/* 资源释放 */

		});

		display.showWindow();

		gameLogicThread.start();

		while (display.running) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

			// 绘制GUI
			guiManager.draw();

			display.update();
		}

		// TODO: 结束游戏时先销毁其它资源 最后再销毁结束游戏时的资源
		// 1. 销毁其它资源
		// 2. 销毁销毁界面的GUI和textures

		textureManager.close();

		try {
			options.close();
			langManager.close();
			shaderResource.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		display.destroy();

	}

}
