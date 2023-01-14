package xueli.mcremake.client.player;

import java.util.function.Supplier;

import xueli.game2.input.KeyBindings.KeyBinding;
import xueli.mcremake.client.CraftGameClient;

public abstract class FunctionalKeyHandler {
	
	protected final CraftGameClient ctx;
	private final Supplier<KeyBinding> keyBindingSupplier;
	
	public FunctionalKeyHandler(CraftGameClient ctx, Supplier<KeyBinding> keyBindingSupplier) {
		this.ctx = ctx;
		this.keyBindingSupplier = keyBindingSupplier;
	}
	
	private boolean lastTimePressed = false;
	
	/**
	 * @return Whether this method need another invoke
	 */
	public boolean tick() {
		KeyBinding keyBinding = keyBindingSupplier.get();
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
