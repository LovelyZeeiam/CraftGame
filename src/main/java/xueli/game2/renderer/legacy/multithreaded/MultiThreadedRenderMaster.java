package xueli.game2.renderer.legacy.multithreaded;

import xueli.game2.lifecycle.LifeCycle;
import xueli.game2.renderer.legacy.RenderMaster;
import xueli.utils.ExecutorThisThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MultiThreadedRenderMaster implements Renderable, LifeCycle {

	private ExecutorService workerExecutor = Executors.newWorkStealingPool();
	private ExecutorThisThread thisThreadExecutor = new ExecutorThisThread();

	private DataRefreshInstance dataRefreshInstance;

	public MultiThreadedRenderMaster() {


	}

	public void callRenderDataRefresh() {
		if(this.dataRefreshInstance != null)
			return;



	}

	@Override
	public void gatherRenderData(Consumer<RenderData> consumer) {
	}

	@Override
	public void init() {


	}

	@Override
	public void tick() {
		thisThreadExecutor.peekAndRunAll();


	}

	@Override
	public void release() {


	}

	ExecutorService getWorkerExecutor() {
		return workerExecutor;
	}

	ExecutorThisThread getThisThreadExecutor() {
		return thisThreadExecutor;
	}

}
