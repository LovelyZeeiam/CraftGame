package xueli.mcremake.classic.client.renderer.world;

import com.flowpowered.nbt.CompoundMap;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;
import xueli.mcremake.classic.core.world.WorldAccessible;

public class BlockRendererSolid implements BlockRenderer {

	private final int x, y;

	public BlockRendererSolid(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(int x, int y, int z, CompoundMap tag, WorldAccessible world, ChunkRenderBuildManager manager) {
		BackRenderBuffer buffer = manager.getRenderBuffer(RenderTypeSolid.class);
		TerrainTexture texture = manager.getRenderer().getTerrainTexture();
		AtlasResourceHolder uvVertex = texture.getUVVertex(this.x, this.y);

		buffer.applyToBuffer(0, new Vector3f(x, y, z), new Vector3f(x + 1, y, z), new Vector3f(x, y, z + 1));
		buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightBottom(), uvVertex.leftTop());
		buffer.applyToBuffer(2, new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));

		buffer.applyToBuffer(0, new Vector3f(x + 1, y, z + 1), new Vector3f(x + 1, y, z), new Vector3f(x, y, z + 1));
		buffer.applyToBuffer(1, uvVertex.rightTop(), uvVertex.rightBottom(), uvVertex.leftTop());
		buffer.applyToBuffer(2, new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));

	}

}
