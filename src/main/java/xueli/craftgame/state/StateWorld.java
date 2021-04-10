package xueli.craftgame.state;

import xueli.game.Game;
import xueli.game.renderer.NVGRenderer;
import xueli.game.renderer.Renderer;

public class StateWorld implements Renderer {

	public static StateWorld INSTANCE_WORLD_LOADING;

	private static enum State {
		LOAD, INGAME, QUIT
	};

	private State state = State.LOAD;

	private NVGRenderer nvgState;
	private StateWorldIngame world;

	private Thread worldInitProcessThread;
	private Thread worldSaveProcessThread;

	// 线程执行报错时 会将这个参数的值改变
	int thread_operate_code = 0;
	Throwable thread_operate_exception;

	public StateWorld(String path) {
		world = new StateWorldIngame(path);
		initThreads();

		setState(State.LOAD);

		INSTANCE_WORLD_LOADING = this;

	}

	public StateWorld(String name, String path) {
		world = new StateWorldIngame(name, path);
		initThreads();

		setState(State.LOAD);

		INSTANCE_WORLD_LOADING = this;

	}

	private void initThreads() {
		worldInitProcessThread = new Thread(() -> world.runLevelInit());
		worldSaveProcessThread = new Thread(() -> world.runLevelSave());

	}

	@Override
	public void render() {
		switch (state) {
		case LOAD: {
			nvgState.update();
			nvgState.render();
			if (!worldInitProcessThread.isAlive()) {
				if (thread_operate_code == 0) {
					setState(State.INGAME);

				} else {

				}
			}
			break;
		}
		case QUIT: {
			nvgState.update();
			nvgState.render();
			if (!worldInitProcessThread.isAlive()) {
				if (thread_operate_code == 0) {
					Game.INSTANCE_GAME.getRendererManager().setCurrentRenderer(new StateMainMenu());

				} else {

				}
			}
			break;
		}
		case INGAME: {
			world.render();

			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + state);
		}

	}

	@Override
	public void update() {

	}

	@Override
	public void size(int w, int h) {
		if (state == State.LOAD || state == State.QUIT) {
			nvgState.size(w, h);

		}

	}

	@Override
	public void release() {
		nvgState.release();
		world.release();

	}

	public void setState(State state) {
		this.state = state;

		if (state == State.LOAD) {
			this.nvgState = new StateSingleLine("#world_loading.text");
			worldInitProcessThread.start();

		} else if (state == State.QUIT) {
			this.nvgState = new StateSingleLine("#world_saving.text");
			worldSaveProcessThread.start();

		}

	}

}
