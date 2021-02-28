package xueli.game.utils.renderer;

public class RendererManager implements Renderer {

	public Renderer current;

	public RendererManager() {

	}

	public void setCurrentRenderer(Renderer renderer) {
		if (this.current != null) {
			this.current.release();
		}
		this.current = renderer;

	}

	@Override
	public void render() {
		if (this.current != null) {
			this.current.render();
		}
	}

	@Override
	public void size(int w, int h) {
		if (this.current != null) {
			this.current.size(w, h);
		}
	}

	@Override
	public void release() {
		if (this.current != null) {
			this.current.release();
		}
	}

}
