package xueli.game2.input;

import xueli.game2.input.KeyBindings.KeyBinding;

public class DefaultInputListener {

	private final KeyBindings context;

	public DefaultInputListener(KeyBindings context) {
		this.context = context;
	}

	public void onInput(int key, boolean pressOrRelease) {
		KeyBinding binding = context.getKeyBinding(key);
		if (pressOrRelease) {
			binding.clickCount++;
			binding.pressed = true;
		} else {
			binding.pressed = false;
		}
//		System.out.println(binding);
	}

}
