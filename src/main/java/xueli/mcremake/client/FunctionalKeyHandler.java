package xueli.mcremake.client;

import java.util.function.Supplier;

import xueli.game2.input.KeyBindings.KeyBinding;

public abstract class FunctionalKeyHandler {
	
	protected final CraftGameClient ctx;
	private final Supplier<KeyBinding> keyBindingSupplier;
	
	public FunctionalKeyHandler(CraftGameClient ctx, Supplier<KeyBinding> keyBindingSupplier) {
		this.ctx = ctx;
		this.keyBindingSupplier = keyBindingSupplier;
	}
	
	/**
	 * @return Whether the game need another "renderTick"
	 */
	public boolean tick() {
		KeyBinding keyBinding = keyBindingSupplier.get();
		while(keyBinding.consumeClick()) {
//			System.out.println("233");
			if(this.functionStart())
				return true;
		}
		if(keyBinding.isPressed()) {
			if(this.functionContinue())
				return true;
		} else {
			return this.functionEnd();
		}
		return false;
	}
	
	protected abstract boolean functionStart();
	
	protected abstract boolean functionContinue();
	
	protected abstract boolean functionEnd();
	
}
