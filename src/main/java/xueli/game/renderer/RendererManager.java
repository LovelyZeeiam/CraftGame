package xueli.game.renderer;

import xueli.game.Game;
import xueli.utils.logger.MyLogger;

@Deprecated
public class RendererManager {

	private Renderer current;

	public RendererManager() {

	}

	public void setCurrentRenderer(Renderer renderer) {
		Game.INSTANCE_GAME.addTaskForMainThread(() -> {
			MyLogger.getInstance().pushState("Renderer");
			if (this.current != null) {
				this.current.release();
			}
			this.current = renderer;
			this.current.size();
			MyLogger.getInstance().info("Change renderer: " + renderer.getClass().getName());
			MyLogger.getInstance().popState();
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
