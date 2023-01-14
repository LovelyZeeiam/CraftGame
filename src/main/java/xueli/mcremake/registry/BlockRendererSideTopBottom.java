package xueli.mcremake.registry;

import org.lwjgl.utils.vector.Vector3f;

import com.flowpowered.nbt.CompoundMap;

import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;
import xueli.mcremake.client.renderer.world.BlockVertexGatherer;
import xueli.mcremake.client.renderer.world.ChunkRenderBuildManager;
import xueli.mcremake.client.renderer.world.RenderTypeSolid;
import xueli.mcremake.core.world.WorldAccessible;

public class BlockRendererSideTopBottom implements BlockVertexGatherer {
	
	private final int sideX, sideY;
	private final int topX, topY;
	private final int bottomX, bottomY;
	
	public BlockRendererSideTopBottom(int sideX, int sideY, int topX, int topY, int bottomX, int bottomY) {
		this.sideX = sideX;
		this.sideY = sideY;
		this.topX = topX;
		this.topY = topY;
		this.bottomX = bottomX;
		this.bottomY = bottomY;
	}

	@Override
	public void render(int x, int y, int z, CompoundMap tag, WorldAccessible world, ChunkRenderBuildManager manager) {
		BackRenderBuffer buffer = manager.getRenderBuffer(RenderTypeSolid.class);
		TerrainTextureAtlas texture = manager.getRenderType(RenderTypeSolid.class).getTexture();
		
		if(world.getBlock(x, y - 1, z) == null) {
			AtlasResourceHolder uvVertex = texture.getUVVertex(this.bottomX, this.bottomY);
			buffer.applyToBuffer(0, new Vector3f(x, y, z), new Vector3f(x + 1, y, z), new Vector3f(x, y, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f));

			// Indicate which plane faces the player by figure out its wrap order
			buffer.applyToBuffer(0, new Vector3f(x + 1, y, z), new Vector3f(x + 1, y, z + 1), new Vector3f(x, y, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f));

		}

		if(world.getBlock(x, y + 1, z) == null) {
			AtlasResourceHolder uvVertex = texture.getUVVertex(this.topX, this.topY);
			buffer.applyToBuffer(0, new Vector3f(x + 1, y + 1, z), new Vector3f(x, y + 1, z), new Vector3f(x, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.leftBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));

			buffer.applyToBuffer(0, new Vector3f(x + 1, y + 1, z + 1), new Vector3f(x + 1, y + 1, z), new Vector3f(x, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightTop(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));

		}

		if(world.getBlock(x - 1, y, z) == null) {
			AtlasResourceHolder uvVertex = texture.getUVVertex(this.sideX, this.sideY);
			buffer.applyToBuffer(0, new Vector3f(x, y, z), new Vector3f(x, y, z + 1), new Vector3f(x, y + 1, z));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

			buffer.applyToBuffer(0, new Vector3f(x, y, z + 1), new Vector3f(x, y + 1, z + 1), new Vector3f(x, y + 1, z));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

		}

		if(world.getBlock(x + 1, y, z) == null) {
			AtlasResourceHolder uvVertex = texture.getUVVertex(this.sideX, this.sideY);
			buffer.applyToBuffer(0, new Vector3f(x + 1, y, z), new Vector3f(x + 1, y + 1, z), new Vector3f(x + 1, y, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftBottom());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

			buffer.applyToBuffer(0, new Vector3f(x + 1, y, z + 1), new Vector3f(x + 1, y + 1, z), new Vector3f(x + 1, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightTop(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

		}

		if(world.getBlock(x, y, z - 1) == null) {
			AtlasResourceHolder uvVertex = texture.getUVVertex(this.sideX, this.sideY);
			buffer.applyToBuffer(0, new Vector3f(x, y, z), new Vector3f(x, y + 1, z), new Vector3f(x + 1, y, z));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftBottom());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

			buffer.applyToBuffer(0, new Vector3f(x, y + 1, z), new Vector3f(x + 1, y + 1, z), new Vector3f(x + 1, y, z));
			buffer.applyToBuffer(1, uvVertex.rightTop(), uvVertex.leftTop(), uvVertex.leftBottom());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

		}

		if(world.getBlock(x, y, z + 1) == null) {
			AtlasResourceHolder uvVertex = texture.getUVVertex(this.sideX, this.sideY);
			buffer.applyToBuffer(0, new Vector3f(x, y, z + 1), new Vector3f(x + 1, y, z + 1), new Vector3f(x, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

			buffer.applyToBuffer(0, new Vector3f(x, y + 1, z + 1), new Vector3f(x + 1, y, z + 1), new Vector3f(x + 1, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftTop(), uvVertex.rightBottom(), uvVertex.rightTop());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

		}
		
	}
	
}
