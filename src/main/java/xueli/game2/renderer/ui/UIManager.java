package xueli.game2.renderer.ui;

import xueli.game2.display.IGameRenderer;
import xueli.game2.lifecycle.LifeCycle;

public class UIManager implements LifeCycle {
	
	protected IGameRenderer renderer;
	
	public UIManager(IGameRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void tick() {
		
	}

	@Override
	public void release() {
		
	}
	
}
