package xueli.game.utils.renderer;

import xueli.game.Game;
import xueli.utils.io.Log;

public class RendererManager implements Renderer {

	public Renderer current;
	public Renderer popout;

	public RendererManager() {

	}

	public void setCurrentRenderer(Renderer renderer) {
		Game.INSTANCE_GAME.addTaskForMainThread(() -> {
			if (this.current != null) {
				this.current.release();
			}
			this.current = renderer;
			this.current.size((int) Game.INSTANCE_GAME.getWidth(), (int) Game.INSTANCE_GAME.getHeight());
			Log.logger.finer("[Renderer] change renderer: " + renderer.getClass().getName());
		});

	}
	
	public void setPopoutRenderer(Renderer popout) {
		Game.INSTANCE_GAME.addTaskForMainThread(() -> {
			if (this.popout != null) {
				this.popout.release();
			}
			this.popout = popout;
			this.popout.size((int) Game.INSTANCE_GAME.getWidth(), (int) Game.INSTANCE_GAME.getHeight());
			Log.logger.finer("[Renderer] pop out: " + popout.getClass().getName());
		});
	}

	@Override
	public void render() {
		if (this.current != null) {
			this.current.render();
		}
		if(this.popout != null) {
			this.popout.render();
		}
	}

	@Override
	public void size(int w, int h) {
		if (this.current != null) {
			this.current.size(w, h);
		}
		if(this.popout != null) {
			this.popout.size(w, h);
		}
	}

	@Override
	public void release() {
		if (this.current != null) {
			this.current.release();
		}
		if(this.popout != null) {
			this.popout.release();
		}
	}

}
