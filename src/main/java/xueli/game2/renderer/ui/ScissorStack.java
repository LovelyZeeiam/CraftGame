package xueli.game2.renderer.ui;

import org.lwjgl.utils.vector.Vector2f;

import java.util.Stack;

public class ScissorStack {

	public static record ScissorResult(float x, float y, float width, float height) {}

	private final MatrixStack matrixStack;

	// Just store the result so that repeat calculation can be saved
	private final Stack<ScissorResult> scissorStack = new Stack<>();

	public ScissorStack(MatrixStack matrixStack) {
		this.matrixStack = matrixStack;
	}

	public ScissorResult push(float x, float y, float width, float height) {
		if(scissorStack.empty()) {
			var thisScissor = new ScissorResult(x, y, width, height);
			this.scissorStack.push(thisScissor);
			return thisScissor;
		}

		var lastScissor = scissorStack.peek();
		var transformedPosition = this.matrixStack.transform(new Vector2f(x, y));
		var transformedSize = this.matrixStack.delta(new Vector2f(width, height));

		float newX1 = Math.max(lastScissor.x, transformedPosition.x);
		float newY1 = Math.max(lastScissor.y, transformedPosition.y);
		float newX2 = Math.min(lastScissor.x + lastScissor.width, transformedPosition.x + transformedSize.x);
		float newY2 = Math.min(lastScissor.y + lastScissor.height, transformedPosition.y + transformedSize.y);
		var newScissor = new ScissorResult(newX1, newY1, newX2 - newX1, newY2 - newY1);
		this.scissorStack.push(newScissor);
		return newScissor;
	}

	public ScissorResult pop() {
		if(scissorStack.empty()) return null;
		return scissorStack.pop();
	}

	public void reset() {
		scissorStack.clear();
	}

}
