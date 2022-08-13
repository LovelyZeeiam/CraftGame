package xueli.craftgame.renderer.blocks;

import xueli.craftgame.renderer.WorldRenderer;

public abstract class BlockRenderable {

	public void gatherRenderData(int x, int y, int z, WorldRenderer renderer) {
		gatherRenderDataActually(x, y, z, renderer);

	}

	public void gatherRenderDataReview(WorldRenderer renderer) {
		gatherRenderDataReviewActually(renderer);

	}

	protected abstract void gatherRenderDataActually(int x, int y, int z, WorldRenderer renderer);

	protected abstract void gatherRenderDataReviewActually(WorldRenderer renderer);

}
