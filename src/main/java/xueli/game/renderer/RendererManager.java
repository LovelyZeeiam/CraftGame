package xueli.game.renderer;

import xueli.game.Game;
import xueli.game.renderer.widgets.Toasts;
import xueli.game.renderer.widgets.Toasts.Type;
import xueli.utils.io.Log;

public class RendererManager {

	private Renderer current;

	private Toasts toasts;

	public RendererManager() {
		this.toasts = new Toasts();

	}

	public void setCurrentRenderer(Renderer renderer) {
		Game.INSTANCE_GAME.addTaskForMainThread(() -> {
			if (this.current != null) {
				this.current.release();
			}
			this.current = renderer;
			this.current.size();
			Log.logger.finer("[Renderer] change renderer: " + renderer.getClass().getName());
		});

	}

	public void message(String title, String message, Type type) {
		toasts.submit(title, message, type);
		Log.logger.info("[Message " + type.toString() + ": " + title + "] " + message);

	}

	public void render() {
		if (this.current != null) {
			this.current.update();
			this.current.render();
		}
		toasts.update();
		toasts.render();

	}

	public void size(int w, int h) {
		if (this.current != null) {
			this.current.size();
		}
		toasts.size();

	}

	public void release() {
		if (this.current != null) {
			this.current.release();
		}
		toasts.release();

	}

}
