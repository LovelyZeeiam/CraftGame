package xueli.mcremake.client.player;

import xueli.game2.input.KeyBindings.KeyBinding;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.IGameSystem;

public abstract class FunctionalKeyHandler implements IGameSystem {
	
	protected final CraftGameClient ctx;
	private KeyBinding keyBinding;
	
	public FunctionalKeyHandler(CraftGameClient ctx, KeyBinding keyBinding) {
		this.ctx = ctx;
		this.keyBinding = keyBinding;
	}
	
	public void setKeyBinding(KeyBinding keyBinding) {
		this.keyBinding = keyBinding;
	}
	
	private boolean lastTimePressed = false;
	
	/**
	 * @return Whether this method need another invoke
	 */
	public void tick(CraftGameClient ctx) {
		while(keyBinding.consumeClick()) {
			this.functionStart(ctx);
			return;
		}
		
		boolean thisTimePressed = keyBinding.isPressed();
		if(thisTimePressed) {
			this.functionContinue(ctx);
		} else if(this.lastTimePressed) {
			this.functionEnd(ctx);
		}
		
		this.lastTimePressed = thisTimePressed;
		
	}
	
	protected abstract void functionStart(CraftGameClient ctx);
	
	protected abstract void functionContinue(CraftGameClient ctx);
	
	protected abstract void functionEnd(CraftGameClient ctx);
	
}
