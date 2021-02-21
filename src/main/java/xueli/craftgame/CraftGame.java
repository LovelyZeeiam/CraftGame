package xueli.craftgame;

import java.io.File;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;

import xueli.craftgame.block.BlockResource;
import xueli.craftgame.block.model.ModelManager;
import xueli.craftgame.net.player.PlayerStat;
import xueli.craftgame.view.widget.WidgetNicknameView;
import xueli.gamengine.IGame;
import xueli.gamengine.resource.GuiResource;
import xueli.gamengine.utils.resource.TaskManager;
import xueli.gamengine.view.GUIFader.Faders;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.GUITextView;
import xueli.gamengine.view.View;
import xueli.utils.Files;
import xueli.utils.Logger;

public class CraftGame extends IGame {

	private static final int WIDTH = 800, HEIGHT = 600;
	public static final int GAME_VERSION = 0;

	public boolean inWorld = false;
	private WorldLogic worldLogic;

	private File workingDirectory;

	public static CraftGame INSTANCE_CRAFT_GAME;

	private PlayerStat playerStat;

	private BlockResource blockResource;
	private ModelManager modelManager;

	public CraftGame() {
		super("res/");

		INSTANCE_CRAFT_GAME = this;

	}

	@Override
	protected void onCreate() {
		GuiResource.addWidget("name_view", WidgetNicknameView.class);

		// 游戏引擎本身的初始化
		initAll(WIDTH, HEIGHT);

		this.workingDirectory = new File(this.getOptions().get("working_directory").getAsString());
		if (!this.workingDirectory.exists()) {
			Logger.error(new ExceptionInInitializerError(
					"Working directory doesn't exist: " + this.workingDirectory.getAbsolutePath()));
		}
		Logger.info("Working directory: " + workingDirectory.getAbsolutePath());

		// 在另一个线程线程初始化在游戏加载时可以初始化的游戏资源
		GameLoader gameLoader = new GameLoader(this);
		Thread gameLoaderThread = new Thread(gameLoader);

		// 显示窗口
		showDisplay();

		// 游戏引擎里面的另一个线程，当有些需要在背景偷偷进行的task进入这里面的列表，就会悄悄在背后执行
		TaskManager.startListener();

		// 启动游戏加载的线程
		gameLoaderThread.start();

		try {
			// 这里得用getPath 并且不能有中文的名字
			String nickname = Files.readAllString(new File(workingDirectory.getPath() + "/player/player_name.txt"));
			int playerIcon = getViewManager().loadTexture(workingDirectory.getPath() + "/player/player_icon.jpg",
					NanoVG.NVG_IMAGE_GENERATE_MIPMAPS);
			this.playerStat = new PlayerStat(nickname, playerIcon);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public PlayerStat getPlayerStat() {
		return playerStat;
	}

	public BlockResource getBlockResource() {
		return blockResource;
	}

	public ModelManager getModelManager() {
		return modelManager;
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

		if (worldLogic != null) {
			worldLogic.delete();

		}

		TaskManager.stopListener();

		releaseAll();

	}

	public class GameLoader implements Runnable {

		// ◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤
		boolean waitingForLove = false;
		boolean sleeping = false;

		private CraftGame cg;

		// 这些东西在构造函数初始化 不然有可能会抛空指针
		/* 资源加载 */
		private View loading_gui;
		private GUIProgressBar loading_ProgressBar;
		private GUITextView loading_TextView;

		public GameLoader(CraftGame cg) {
			this.cg = cg;

			/* 资源加载 */
			loading_gui = guiResource.getGui("game_loading.json");
			loading_ProgressBar = (GUIProgressBar) loading_gui.widgets.get("loading_progress_bar");
			loading_TextView = (GUITextView) loading_gui.widgets.get("loading_message");

			viewManager.setFadeinGui(loading_gui, Faders.LINEAR.fader);

		}

		@Override
		public void run() {
			boolean loadingSuccess = false;
			String[] failedMessage = new String[1];

			try {
				/* 资源加载 */
				String loading_messageString = loading_TextView.getText();

				// 设置加载动画
				queueRunningInMainThread.add(() -> loading_gui.setAnimation("loading"));

				// 加载GUI
				guiResource.loadGui(langManager, loading_TextView, loading_ProgressBar, 0.00f, 1.00f);

				loading_TextView.setText(loading_messageString);

				// 设置监听
				View mainMenuGui = guiResource.getGui("main_menu.json");

				mainMenuGui.widgets.get("single_player_button").onClickListener = (button, action, offsetX,
						offsetY) -> {
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

				/*
				 * mainMenuGui.widgets.get("setting_button").onClickListener = (button, action,
				 * offsetX, offsetY) -> { if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT & action ==
				 * GLFW.GLFW_RELEASE) { queueRunningInMainThread.add(() ->
				 * viewManager.setGui("game_setting.json"));
				 * 
				 * } };
				 */

				View esc_menu = cg.getGuiResource().getGui("game_esc_menu.json");
				esc_menu.widgets.get("back_to_game_button").onClickListener = (button, action, offsetX, offsetY) -> {
					if (action == GLFW.GLFW_RELEASE) {
						// 从esc界面回到游戏中

					}

				};

				esc_menu.widgets.get("quit_button").onClickListener = (button, action, offsetX, offsetY) -> {
					if (action == GLFW.GLFW_RELEASE) {
						worldLogic.delete();
						worldLogic = null;

						guiResource.loadGui("world_loading.json", langManager, true);

						System.gc();

					}

				};

				View failedView = cg.getGuiResource().getGui("server_connect_failed.json");

				failedView.widgets.get("return_button").onClickListener = (button, action, offsetX, offsetY) -> {
					if (action == GLFW.GLFW_RELEASE) {
						worldLogic = null;
						viewManager.setGui("main_menu.json");
						guiResource.loadGui("world_loading.json", langManager, true);

						System.gc();

					}
				};

				modelManager = new ModelManager();
				modelManager.init();

				blockResource = new BlockResource("res/");
				blockResource.load();

				loadingSuccess = true;
			} catch (Exception e) {
				failedMessage[0] = e.getClass().getName() + ": " + e.getMessage();
				loadingSuccess = false;

				e.printStackTrace();

			}

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

			if (loadingSuccess) {
				queueRunningInMainThread.add(() -> {
					loading_TextView.setText("Loaded successfully~");

					// 换界面!
					viewManager.setFadeinGui("main_menu.json", Faders.LINEAR.fader);

				});

			} else {
				queueRunningInMainThread.add(() -> {
					display.setSubtitle("Loading failed");
					loading_TextView.setText("Loaded failed, please check console: " + failedMessage[0]);

				});

			}

		}

	}

}
