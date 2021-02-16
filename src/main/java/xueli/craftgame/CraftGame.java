package xueli.craftgame;

import org.lwjgl.glfw.GLFW;

import xueli.craftgame.block.BlockResource;
import xueli.craftgame.world.biome.BiomeResource;
import xueli.craftgame.world.biome.Biomes;
import xueli.gamengine.IGame;
import xueli.gamengine.utils.resource.TaskManager;
import xueli.gamengine.view.GUIFader.Faders;
import xueli.gamengine.view.GUIImageView;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.GUITextView;
import xueli.gamengine.view.View;

public class CraftGame extends IGame {

	private static int width = 800, height = 600;
	public boolean inWorld = false;
	private WorldLogic worldLogic;

	public CraftGame() {
		super("res/");

	}

	@Override
	protected void onCreate() {
		// 游戏引擎本身的初始化
		initAll(width, height);

		// 在另一个线程线程初始化在游戏加载时可以初始化的游戏资源
		GameLoader gameLoader = new GameLoader(this);
		Thread gameLoaderThread = new Thread(gameLoader);

		// 显示窗口
		showDisplay();

		// 游戏引擎里面的另一个线程，当有些需要在背景偷偷进行的task进入这里面的列表，就会悄悄在背后执行
		TaskManager.startListener();

		// 启动游戏加载的线程
		gameLoaderThread.start();

	}

	@Override
	protected void onSized() {
		if (worldLogic != null)
			worldLogic.size();

	}

	@Override
	protected void onCursorPos(double dx, double dy) {
		if (worldLogic != null)
			worldLogic.mouseMove(dx, dy);

	}

	@Override
	protected void onMouseButton(int button) {
		if (worldLogic != null)
			worldLogic.onMouseClick(button);

	}

	@Override
	protected void onKeyboard(int key) {
		if (worldLogic != null)
			worldLogic.onKeyboard(key);

	}

	@Override
	protected void onScroll(float scroll) {
		if (worldLogic != null) {
			worldLogic.onMouseScroll(scroll);

		}

	}

	@Override
	protected void onDrawFrame() {
		try {
			// 当在世界内部时，就渲染世界，否则就渲染游戏GUI
			if (inWorld) {
				worldLogic.draw();
			} else {
				viewManager.draw();
			}

			// 有些任务只能在主线程做才不会出问题，这个方法会让主线程每次渲染时执行这个列表里面的一个方法，防止卡顿
			runQueueList();

		} catch (Exception e) {
			// 有些时候可能会在开发过程中出exception，此时其它的线程仍然在运行，窗口还卡死着，就会让开发者束手无策
			// 于是干脆出异常就将程序退出算了~
			e.printStackTrace();
			System.exit(-1);
		}

	}

	@Override
	protected void onExit() {
		if (inWorld) {
			worldLogic.running = false;
		}

		TaskManager.stopListener();

		releaseAll();

		System.exit(0);

	}

	public class GameLoader implements Runnable {

		// ◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤
		boolean waitingForLove = false;
		boolean sleeping = false;

		BlockResource resource;

		private CraftGame cg;

		// 这些东西在构造函数初始化 不然有可能会抛空指针
		/* 资源加载 */
		private View loading_gui;
		private GUIImageView loading_imageView;
		private GUIProgressBar loading_ProgressBar;
		private GUITextView loading_TextView;

		public GameLoader(CraftGame cg) {
			this.cg = cg;

			/* 资源加载 */
			loading_gui = guiResource.getGui("game_loading.json");
			loading_imageView = (GUIImageView) loading_gui.widgets.get("loading_splash");
			loading_ProgressBar = (GUIProgressBar) loading_gui.widgets.get("loading_progress_bar");
			loading_TextView = (GUITextView) loading_gui.widgets.get("loading_message");

			viewManager.setFadeinGui(loading_gui, Faders.LINEAR.fader);

		}

		@Override
		public void run() {
			/* 资源加载 */
			String loading_messageString = loading_TextView.getText();

			// 设置加载动画
			queueRunningInMainThread.add(() -> loading_gui.setAnimation("loading"));

			// 加载GUI
			guiResource.loadGui(langManager, loading_TextView, loading_ProgressBar, 0.00f, 1.00f);

			loading_TextView.setText(loading_messageString);

			// 设置监听
			View mainMenuGui = guiResource.getGui("main_menu.json");

			mainMenuGui.widgets.get("single_player_button").onClickListener = (button, action, offsetX, offsetY) -> {
				if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT & action == GLFW.GLFW_RELEASE) {
					waitingForLove = false;

					queueRunningInMainThread.add(() -> viewManager.setGui("world_loading.json"));

					System.gc();

					worldLogic = new WorldLogic(cg);
					worldLogic.running = true;
					new Thread(worldLogic).start();

				}

			};

			mainMenuGui.widgets.get("multi_player_button").onClickListener = (button, action, offsetX, offsetY) -> {

			};
			mainMenuGui.widgets.get("setting_button").onClickListener = (button, action, offsetX, offsetY) -> {
				if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT & action == GLFW.GLFW_RELEASE) {
					queueRunningInMainThread.add(() -> viewManager.setGui("game_setting.json"));

				}
			};

			View esc_menu = cg.getGuiResource().getGui("game_esc_menu.json");
			esc_menu.widgets.get("back_to_game_button").onClickListener = (button, action, offsetX, offsetY) -> {
				if (action == GLFW.GLFW_RELEASE) {
					// 从esc界面回到游戏中

				}

			};

			esc_menu.widgets.get("quit_button").onClickListener = (button, action, offsetX, offsetY) -> {
				if (action == GLFW.GLFW_RELEASE) {
					inWorld = false;
					worldLogic.delete();
					worldLogic = null;
					viewManager.setGui("main_menu.json");

					guiResource.loadGui("world_loading.json", langManager, true);

					System.gc();

				}

			};

			queueRunningInMainThread.add(() -> {
				loading_TextView.setText("Loading...");

			});

			loading_ProgressBar.setProgress(1.0f);

			waitingForLove = true;
			sleeping = true;

			// 等待直到进度条到底
			loading_ProgressBar.waitUtilProgressFull();

			sleeping = false;
			waitingForLove = false;

			queueRunningInMainThread.add(() -> {
				loading_TextView.setText("Loaded successfully~");

				// 换界面!
				viewManager.setFadeinGui("main_menu.json", Faders.LINEAR.fader);

			});

		}

	}

}
