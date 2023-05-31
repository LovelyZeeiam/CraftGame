package xueli.game2.renderer.ui;

import static org.lwjgl.nanovg.NanoVGGL3.nvgluBindFramebuffer;

import java.util.Stack;

import org.lwjgl.nanovg.NVGLUFramebuffer;

//Push this frame buffer into the stack instead of just bind it
// so that when calling "pop" the last frame buffer can be bound
// This is really a good feature
public class FrameBufferStack {

	private final NanoGui gui;
	private final Stack<NanoFrameBuffer> framebuffers = new Stack<>();

	public FrameBufferStack(NanoGui gui) {
		this.gui = gui;
	}

	public void push(NanoFrameBuffer framebuffer) {
		framebuffers.push(framebuffer);
		this.syncToTopElement();
	}

	public void pop() {
		framebuffers.pop();
		this.syncToTopElement();
	}

	public boolean isEmpty() {
		return framebuffers.empty();
	}

	private void syncToTopElement() {
		if (framebuffers.empty()) {
			nvgluBindFramebuffer(gui.nvg, null);
			return;
		}
		NVGLUFramebuffer top = framebuffers.peek().rawFramebuffer;
		if (top == null)
			nvgluBindFramebuffer(gui.nvg, null);
		else
			nvgluBindFramebuffer(gui.nvg, top);
		

	}

}
