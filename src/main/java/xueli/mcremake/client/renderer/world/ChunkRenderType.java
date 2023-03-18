package xueli.mcremake.client.renderer.world;

import org.lwjgl.utils.vector.Vector2i;

import xueli.game2.math.Frustum;
import xueli.game2.renderer.legacy.RenderType;
import xueli.mcremake.core.world.Chunk;

public abstract class ChunkRenderType extends RenderType<Vector2i> {

	public ChunkRenderType() {
		super(v -> new MyRenderBuffer());
	}
	
	public void cullRender(Frustum frustum) {
		this.render(v -> {
//			return true;
			return frustum.isCubeInFrustum(v.x * 16, 0, v.y * 16, (v.x + 1) * 16, Chunk.CHUNK_HEIGHT, (v.y + 1) * 16);
		});
		
	}
	
}
