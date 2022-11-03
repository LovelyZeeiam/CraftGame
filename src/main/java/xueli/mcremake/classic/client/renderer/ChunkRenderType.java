package xueli.mcremake.classic.client.renderer;

import xueli.game.vector.Vector2i;
import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.renderer.legacy.RenderType;

import java.util.function.Function;

public abstract class ChunkRenderType extends RenderType<Vector2i> {

	public ChunkRenderType(Function<Vector2i, RenderBuffer> bufferSupplier) {
		super(bufferSupplier);
	}

}
