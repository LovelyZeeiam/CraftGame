package xueli.game2.input;

import org.lwjgl.glfw.GLFW;

import xueli.game2.display.KeyInputListener;

public class DefaultKeyListener extends DefaultInputListener implements KeyInputListener {
	
	public DefaultKeyListener(KeyBindings context) {
		super(context);
	}

	@Override
	public void onKey(int key, int scancode, int action, int mods) {
		if(action == GLFW.GLFW_PRESS) {
			this.onInput(key, true);
		} else if(action == GLFW.GLFW_RELEASE) {
			this.onInput(key, false);
		}
		
	}

}
