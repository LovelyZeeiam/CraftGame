package xueli.game.ui;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;

import java.util.Stack;

import xueli.game.Game;
import xueli.game.renderer.Renderer;

public class UIRenderer implements Renderer {

	private Game game;
	long nvg;

	private ImageManager imageManager;

	private Stack<UIView> views = new Stack<>();

	public UIRenderer(Game game) {
		this.game = game;

		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			game.announceCrash("UI Init", new RuntimeException("Couldn't init NanoVG!"));
		}

		this.imageManager = new ImageManager(this);

	}

	public void pushUIView(UIView view) {
		views.push(view);
	}

	public void popUIView() {
		UIView view = views.pop();
		if (view != null) {
			view.release();
		}
	}

	@Override
	public void render() {
		views.forEach(view -> {
			view.update();
		});

		nvgBeginFrame(nvg, game.getWidth(), game.getHeight(), game.getWidth() / game.getHeight());
		views.forEach(view -> {
			view.render();
		});
		nvgEndFrame(nvg);

	}

	@Override
	public void update() {
	}

	@Override
	public void size() {
		views.forEach(view -> {
			view.requestRepaint();
		});
	}

	@Override
	public void release() {
		views.forEach(view -> {
			view.release();
		});

		imageManager.release();

	}

	public Game getGame() {
		return game;
	}

	public long getNvg() {
		return nvg;
	}

	public ImageManager getImageManager() {
		return imageManager;
	}

}
