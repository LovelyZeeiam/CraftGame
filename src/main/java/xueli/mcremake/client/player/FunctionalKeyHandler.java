package xueli.mcremake.client.player;

import xueli.game2.input.KeyBindings.KeyBinding;
import xueli.mcremake.client.CraftGameClient;

public abstract class FunctionalKeyHandler {
	
	protected final CraftGameClient ctx;
	private final KeyBinding keyBinding;
	
	public FunctionalKeyHandler(CraftGameClient ctx, KeyBinding keyBinding) {
		this.ctx = ctx;
		this.keyBinding = keyBinding;
	}
	
	private boolean lastTimePressed = false;
	
	/**
	 * @return Whether this method need another invoke
	 */
	public boolean tick() {
		while(keyBinding.consumeClick()) {
			this.functionStart();
			return true;
		}
		
		boolean thisTimePressed = keyBinding.isPressed();
		if(thisTimePressed) {
			this.functionContinue();
		} else if(this.lastTimePressed) {
			this.functionEnd();
		}
		
		this.lastTimePressed = thisTimePressed;
		return false;
	}
	
	protected abstract void functionStart();
	
	protected abstract void functionContinue();
	
	protected abstract void functionEnd();
	
}
