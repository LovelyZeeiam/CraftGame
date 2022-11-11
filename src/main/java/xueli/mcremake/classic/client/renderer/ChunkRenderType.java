package xueli.mcremake.classic.client.renderer;

import xueli.game.vector.Vector2i;
import xueli.game2.renderer.legacy.DefaultRenderBuffer;
import xueli.game2.renderer.legacy.RenderType;
import xueli.game2.renderer.legacy.ShapeType;

public abstract class ChunkRenderType extends RenderType<Vector2i> {

	public ChunkRenderType() {
		super(v -> new DefaultRenderBuffer(ShapeType.TRIANGLES));
	}

}
