package xueli.game2.input;

import java.util.HashMap;

/**
 * Might add DirectX support to the engine, then there should be lots of public constant, each one for each key
 */
public class KeyBindings {
	
	private final HashMap<Integer, KeyBinding> bindings = new HashMap<>();
	
	public KeyBindings() {
	}
	
	public KeyBinding getKeyBinding(int keyId) {
		return bindings.computeIfAbsent(keyId, i -> new KeyBinding());
	}
	
	public boolean consumeClick(int keyId) {
		return this.getKeyBinding(keyId).consumeClick();
	}
	
	public boolean isPressed(int keyId) {
		return this.getKeyBinding(keyId).isPressed();
	}
	
	public static class KeyBinding {
		
		int clickCount = 0;
		boolean pressed = false;
		
		public boolean consumeClick() {
			if(clickCount > 0) {
				clickCount--;
				return true;
			}
			return false;
		}
		
		public boolean isPressed() {
			return pressed;
		}

		@Override
		public String toString() {
			return "KeyBinding [clickCount=" + clickCount + ", pressed=" + pressed + "]";
		}
		
	}
	
}
