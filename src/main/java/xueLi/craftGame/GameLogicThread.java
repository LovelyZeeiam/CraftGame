package xueLi.craftGame;

import org.lwjgl.glfw.GLFW;

import xueLi.gamengine.view.GUIImageView;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.GUITextView;
import xueLi.gamengine.view.View;
import static xueLi.craftGame.CraftGame.*;

public class GameLogicThread implements Runnable {
	
	Object waiter = new Object();
	// ◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤◢◤
	boolean waitingForLove = false;
	boolean sleeping = false;
	
	static BlockResource resource;

	@Override
	public void run() {
		/* 资源加载 */
		View loading_gui = viewManager.setFadeinGui("game_loading.json");
		
		GUIImageView loading_imageView = (GUIImageView) loading_gui.widgets.get("loading_splash");
		GUIProgressBar loading_ProgressBar = (GUIProgressBar) loading_gui.widgets.get("loading_progress_bar");
		GUITextView loading_TextView = (GUITextView) loading_gui.widgets.get("loading_message");

		String loading_messageString = loading_TextView.getText();

		// 设置加载动画
		loading_imageView.setAnimation("loading");
		// 加载GUI
		guiResource.loadGui(langManager, loading_TextView, loading_ProgressBar, 0.00f, 0.25f);

		loading_TextView.setText(loading_messageString);

		// 设置监听
		View mainMenuGui = guiResource.getGui("main_menu.json");

		mainMenuGui.widgets.get("single_player_button").onClickListener = (button) -> {
			if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
				synchronized (waiter) {
					waiter.notify();
				}
				waitingForLove = false;
				
				viewManager.setGui("world_loading.json");
				
			}

		};

		mainMenuGui.widgets.get("multi_player_button").onClickListener = (button) -> {
			
		};
		mainMenuGui.widgets.get("setting_button").onClickListener = (button) -> {
			
		};

		// 加载方块
		resource = new BlockResource("res/");
		resource.load(loading_TextView, loading_ProgressBar, 0.25f, 1.00f);
		
		// 加载物品

		// 等待直到进度条到底
		loading_TextView.setText("Loading...");
		
		waitingForLove = true;
		sleeping = true;
		loading_ProgressBar.waitUtilProgressFull();
		sleeping = false;
		waitingForLove = false;
		
		loading_TextView.setText("Loaded successfully~");

		// 换界面!
		viewManager.setFadeinGui("main_menu.json");

		// 休息 不要先斩后奏哈哈哈哈哈
		waitingForLove = true;
		synchronized (waiter) {
			try {
				waiter.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		waitingForLove = false;
		
		/* Game Logic */
		
		View world_loading_gui = guiResource.getGui("world_loading.json");
		GUIProgressBar world_loading_progressBar = (GUIProgressBar) world_loading_gui.widgets.get("loading_bar");
		
		
		
		
		

		/* 资源释放 */
		
		resource.close();
		
	}
	
}
