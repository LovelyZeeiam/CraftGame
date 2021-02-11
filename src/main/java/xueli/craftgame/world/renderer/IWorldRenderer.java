package xueli.craftgame.world.renderer;

import xueli.craftgame.world.World;
import xueli.gamengine.utils.renderer.Renderer;

public abstract class IWorldRenderer {

	protected World world;
	protected Renderer renderer;

	public IWorldRenderer(World world, int bufferSize, int bufferType) {
		this.world = world;
		this.renderer = new Renderer(bufferSize, bufferType);

	}

	public abstract void render();

	public abstract void size();

	protected abstract void release();

	public void close() {
		renderer.delete();
		release();

	}

}
