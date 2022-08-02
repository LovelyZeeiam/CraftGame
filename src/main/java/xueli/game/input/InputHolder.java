package xueli.game.input;

import java.util.ArrayList;

import xueli.craftgame.client.renderer.display.KeyInputListener;
import xueli.utils.Asserts;

// TODO: SHOULD USED IN ALL INPUT LISTENER
public class InputHolder {

	private final InputManager ctx;
	final int weight;
	// Whether to pass the key signal to next input holder
	boolean passInterrupt = false;

	ArrayList<KeyInputListener> listeners = new ArrayList<>();

	InputHolder(int weight, InputManager ctx) {
		this.ctx = ctx;
		this.weight = weight;

	}

	public InputHolder setPassInterrupt(boolean passInterrupt) {
		boolean informFlag = passInterrupt != this.passInterrupt;
		this.passInterrupt = passInterrupt;
		if (informFlag) {
			ctx.shouldRecalculateWeightMax = true;
		}
		return this;
	}

	public void addListener(KeyInputListener listener) {
		this.listeners.add(listener);
	}

	void doInputListener(int key, int scancode, int action, int mods) {
		listeners.forEach(l -> l.onInput(key, scancode, action, mods));
	}

	public double getWheelDelta() {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight), () -> ctx.ctx.getDisplay().getWheelDelta(),
				0.0);
	}

	public boolean isKeyDown(int key) {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight), () -> ctx.ctx.getDisplay().isKeyDown(key),
				false);
	}

	public boolean isKeyDownOnce(int key) {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight),
				() -> ctx.ctx.getDisplay().isKeyDownOnce(key), false);
	}

	public boolean isMouseDown(int mouse) {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight),
				() -> ctx.ctx.getDisplay().isMouseDown(mouse), false);
	}

	public boolean isMouseDownOnce(int mouse) {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight),
				() -> ctx.ctx.getDisplay().isMouseDownOnce(mouse), false);
	}

	public float getCursorX() {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight), () -> ctx.ctx.getDisplay().getCursorX(),
				0.0f);
	}

	public float getCursorY() {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight), () -> ctx.ctx.getDisplay().getCursorY(),
				0.0f);
	}

	public float getCursor_dx() {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight), () -> ctx.ctx.getDisplay().getCursor_dx(),
				0.0f);
	}

	public float getCursor_dy() {
		return Asserts.assertBooleanAndMethod(ctx.canReceiveSignal(weight), () -> ctx.ctx.getDisplay().getCursor_dy(),
				0.0f);
	}

	public boolean isMouseGrabbed() {
		return ctx.ctx.getDisplay().isMouseGrabbed();
	}

}
