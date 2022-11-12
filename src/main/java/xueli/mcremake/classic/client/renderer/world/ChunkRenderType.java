package xueli.mcremake.classic.client.renderer.world;

import xueli.game.vector.Vector2i;
import xueli.game2.renderer.legacy.engine.RenderType;

public abstract class ChunkRenderType extends RenderType<Vector2i> {

	public ChunkRenderType() {
		super(v -> new MyRenderBuffer());
	}

}
