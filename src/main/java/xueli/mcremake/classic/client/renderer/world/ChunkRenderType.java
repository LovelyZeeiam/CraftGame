package xueli.mcremake.classic.client.renderer.world;

import xueli.game.vector.Vector2i;
import xueli.game2.renderer.legacy.RenderType;

public abstract class ChunkRenderType extends RenderType<Vector2i> {

	protected final WorldRenderer renderer;

	public ChunkRenderType(WorldRenderer renderer) {
		super(v -> new MyRenderBuffer());
		this.renderer = renderer;
	}

}
