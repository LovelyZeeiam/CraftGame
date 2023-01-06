package xueli.game2.input;

import org.lwjgl.glfw.GLFW;

import xueli.game2.display.MouseInputListener;

public class DefaultMouseListener extends DefaultInputListener implements MouseInputListener {

	public DefaultMouseListener(KeyBindings context) {
		super(context);
	}

	@Override
	public void onMouseButton(int button, int action, int mods) {
		if(action == GLFW.GLFW_PRESS) {
			this.onInput(button, true);
		} else if(action == GLFW.GLFW_RELEASE) {
			this.onInput(button, false);
		}
		
		
	}

}
