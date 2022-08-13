package xueli.craftgame.renderer;

import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;

import java.lang.reflect.Constructor;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.event.EventRaiseGUI;
import xueli.craftgame.renderer.view.IngameView;
import xueli.utils.ExecutorThisThread;
import xueli.game.input.InputHolder;
import xueli.game.input.InputManager;
import xueli.utils.Asserts;

public class GameViewRenderer implements IGameRenderer {

	private CraftGameContext ctx;
	private InputHolder inputHolder;
	private ExecutorThisThread executor = new ExecutorThisThread();

	protected long nvg;
	private volatile IngameView currentView;

	public GameViewRenderer(CraftGameContext ctx) {
		this.ctx = ctx;
		this.inputHolder = ctx.getInputManager().createInputHolder(InputManager.WEIGH_GUI);

	}

	@Override
	public void init() {
		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			ctx.announceCrash("HUD Init", new Exception("Couldn't init NanoVG!"));
		}

	}

	@Override
	public void render() {
		boolean hasView = hasView();
		inputHolder.setPassInterrupt(hasView);
		ctx.getDisplay().setMouseGrabbed(!hasView);

		Asserts.assertNullVoid(currentView, v -> {
			v.render(nvg);
		});

		executor.peekAndRunAll();

	}

	@Override
	public void onSize(int width, int height) {
		IGameRenderer.super.onSize(width, height);

		Asserts.assertNullVoid(currentView, v -> v.onSize(nvg, width, height));

	}

	@Override
	public void release() {
		Asserts.assertNullVoid(currentView, v -> v.release(nvg));

	}

	public void onRaiseView(EventRaiseGUI event) {
		try {
			Class<? extends IngameView> viewClazz = event.getViewClass();

			if (isViewRaised(viewClazz))
				return;
			if (viewClazz != null) {
				Constructor<? extends IngameView> constructor = viewClazz.getConstructor(GameViewRenderer.class);
				IngameView view = constructor.newInstance(this);
				setCurrentView(view);
			} else {
				setCurrentView(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCurrentView(IngameView currentView) {
		executor.execute(() -> {
			Asserts.assertNullVoid(this.currentView, v -> v.release(nvg));
			this.currentView = currentView;
			Asserts.assertNullVoid(currentView, v -> v.init(nvg));
		});
	}

	public IngameView getCurrentView() {
		return currentView;
	}

	public CraftGameContext getContext() {
		return ctx;
	}

	public InputHolder getInputHolder() {
		return inputHolder;
	}

	public boolean isViewRaised(Class<? extends IngameView> viewClazz) {
		return currentView != null && currentView.getClass().equals(viewClazz);
	}

	public boolean hasView() {
		return currentView != null;
	}

}
