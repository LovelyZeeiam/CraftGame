package xueli.craftgame;

import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.View;

public class WorldLogic implements Runnable {

	private final CraftGame cg;
	public boolean running = false;
	
	private boolean isRemote = false;
	
	@Override
	public void run() {
		cg.queueRunningInMainThread.add(this::size);
		
		View loadingView = cg.getGuiResource().getGui("game_loading.json");
		GUIProgressBar progressBar = (GUIProgressBar) loadingView.widgets.get("loading_bar");
		
		
		
	}

	public WorldLogic(CraftGame cg) {
		this.cg = cg;
		
		
	}

	public CraftGame getCg() {
		return cg;
	}
	
	public void size() {
		
	}

	public void mouseMove(double dx, double dy) {
		
	}

	public void onMouseClick(int button) {
		
	}

	public void onKeyboard(int key) {
		
	}

	public void onMouseScroll(float scroll) {
		
	}

	public void draw() {
		
	}

	public void delete() {
		
	}
	
}
