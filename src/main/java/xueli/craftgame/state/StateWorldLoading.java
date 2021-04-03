package xueli.craftgame.state;

import xueli.game.Game;
import xueli.game.utils.renderer.NVGRenderer;
import xueli.game.utils.renderer.Renderer;

public class StateWorldLoading implements Renderer {
	
	public static StateWorldLoading INSTANCE_WORLD_LOADING;

	private static enum State {
		LOAD,INGAME,QUIT
	};
	
	private State state = State.LOAD;
	
	private NVGRenderer nvgState;
	private StateWorld world;
	
	private Thread worldInitProcessThread;
	private Thread worldSaveProcessThread;
	
	// 线程执行报错时 会将这个参数的值改变
	int thread_operate_code = 0;
	Throwable thread_operate_exception;
	
	public StateWorldLoading(String path) {
		world = new StateWorld(path);
		initThreads();
		
		setState(State.LOAD);
		
		INSTANCE_WORLD_LOADING = this;
		
	}

	public StateWorldLoading(String name, String path) {
		world = new StateWorld(name, path);
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
			nvgState.render();
			if(!worldInitProcessThread.isAlive()) {
				if(thread_operate_code == 0) {
					setState(State.INGAME);
					
				} else {
					
					
				}
			}
			break;
		}
		case QUIT:{
			nvgState.render();
			if(!worldInitProcessThread.isAlive()) {
				if(thread_operate_code == 0) {
					Game.INSTANCE_GAME.getRendererManager().setCurrentRenderer(new StateMainMenu());
					
				} else {
					
					
				}
			}
			break;
		}
		case INGAME:{
			world.render();
			
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + state);
		}
		
	}

	@Override
	public void size(int w, int h) {
		if(state == State.LOAD || state == State.QUIT) {
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
		
		if(state == State.LOAD) {
			this.nvgState = new StateSingleLine("#world_loading.text");
			worldInitProcessThread.start();
			
		} else if(state == State.QUIT) {
			this.nvgState = new StateSingleLine("#world_saving.text");
			worldSaveProcessThread.start();
			
		}
		
		
	}
	
}
