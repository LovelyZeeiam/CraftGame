package xueli.game.renderer;

import java.util.logging.Logger;

import xueli.game.Game;

public class RendererManager {

	private Renderer current;

	public RendererManager() {

	}

	public void setCurrentRenderer(Renderer renderer) {
		Game.INSTANCE_GAME.addTaskForMainThread(() -> {
			if (this.current != null) {
				this.current.release();
			}
			this.current = renderer;
			this.current.size();
			Logger.getLogger(getClass().getName())
					.finer("[Renderer] change renderer: " + renderer.getClass().getName());
		});

	}

	public void render() {
		if (this.current != null) {
			this.current.update();
			this.current.render();
		}

	}

	public void size(int w, int h) {
		if (this.current != null) {
			this.current.size();
		}

	}

	public void release() {
		if (this.current != null) {
			this.current.release();
		}

	}

}
