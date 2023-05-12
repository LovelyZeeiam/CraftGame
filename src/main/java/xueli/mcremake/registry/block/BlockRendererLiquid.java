package xueli.mcremake.registry.block;

import org.lwjgl.utils.vector.Vector3f;

import com.flowpowered.nbt.CompoundMap;

import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;
import xueli.mcremake.client.renderer.world.BlockVertexGatherer;
import xueli.mcremake.client.renderer.world.ChunkRenderBuildManager;
import xueli.mcremake.client.renderer.world.RenderTypeAlpha;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.world.WorldAccessible;
import xueli.mcremake.registry.GameRegistry;
import xueli.mcremake.registry.TerrainTextureAtlas;

public class BlockRendererLiquid implements BlockVertexGatherer {
    
    private final int x, y;

	public BlockRendererLiquid(int xInAtlas, int yInAtlas) {
		this.x = xInAtlas;
		this.y = yInAtlas;
	}

	@Override
	public void render(int x, int y, int z, CompoundMap tag, WorldAccessible world, ChunkRenderBuildManager manager) {
		BackRenderBuffer buffer = manager.getRenderBuffer(RenderTypeAlpha.class);
		TerrainTextureAtlas texture = manager.getRenderType(RenderTypeAlpha.class).getTexture();
		AtlasResourceHolder uvVertex = texture.getUVVertex(this.x, this.y);

		if(world == null || shouldRenderFaceOnThisBlock(world.getBlock(x, y - 1, z))) {
			buffer.applyToBuffer(0, new Vector3f(x, y, z), new Vector3f(x + 1, y, z), new Vector3f(x, y, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f));

			// Indicate which plane faces the player by figure out its wrap order
			buffer.applyToBuffer(0, new Vector3f(x + 1, y, z), new Vector3f(x + 1, y, z + 1), new Vector3f(x, y, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.5f, 0.5f, 0.5f));

		}

		if(world == null || shouldRenderFaceOnThisBlock(world.getBlock(x, y + 1, z))) {
			buffer.applyToBuffer(0, new Vector3f(x + 1, y + 1, z), new Vector3f(x, y + 1, z), new Vector3f(x, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.leftBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));

			buffer.applyToBuffer(0, new Vector3f(x + 1, y + 1, z + 1), new Vector3f(x + 1, y + 1, z), new Vector3f(x, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightTop(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1));

		}

		if(world == null || shouldRenderFaceOnThisBlock(world.getBlock(x - 1, y, z))) {
			buffer.applyToBuffer(0, new Vector3f(x, y, z), new Vector3f(x, y, z + 1), new Vector3f(x, y + 1, z));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

			buffer.applyToBuffer(0, new Vector3f(x, y, z + 1), new Vector3f(x, y + 1, z + 1), new Vector3f(x, y + 1, z));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

		}

		if(world == null || shouldRenderFaceOnThisBlock(world.getBlock(x + 1, y, z))) {
			buffer.applyToBuffer(0, new Vector3f(x + 1, y, z), new Vector3f(x + 1, y + 1, z), new Vector3f(x + 1, y, z + 1));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftBottom());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

			buffer.applyToBuffer(0, new Vector3f(x + 1, y, z + 1), new Vector3f(x + 1, y + 1, z), new Vector3f(x + 1, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightTop(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

		}

		if(world == null || shouldRenderFaceOnThisBlock(world.getBlock(x, y, z - 1))) {
			buffer.applyToBuffer(0, new Vector3f(x, y, z), new Vector3f(x, y + 1, z), new Vector3f(x + 1, y, z));
			buffer.applyToBuffer(1, uvVertex.rightBottom(), uvVertex.rightTop(), uvVertex.leftBottom());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

			buffer.applyToBuffer(0, new Vector3f(x, y + 1, z), new Vector3f(x + 1, y + 1, z), new Vector3f(x + 1, y, z));
			buffer.applyToBuffer(1, uvVertex.rightTop(), uvVertex.leftTop(), uvVertex.leftBottom());
			buffer.applyToBuffer(2, new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.7f, 0.7f, 0.7f));

		}

		if(world == null || shouldRenderFaceOnThisBlock(world.getBlock(x, y, z + 1))) {
			buffer.applyToBuffer(0, new Vector3f(x, y, z + 1), new Vector3f(x + 1, y, z + 1), new Vector3f(x, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftBottom(), uvVertex.rightBottom(), uvVertex.leftTop());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

			buffer.applyToBuffer(0, new Vector3f(x, y + 1, z + 1), new Vector3f(x + 1, y, z + 1), new Vector3f(x + 1, y + 1, z + 1));
			buffer.applyToBuffer(1, uvVertex.leftTop(), uvVertex.rightBottom(), uvVertex.rightTop());
			buffer.applyToBuffer(2, new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(0.8f, 0.8f, 0.8f));

		}

	}

    private boolean shouldRenderFaceOnThisBlock(BlockType block) {
        return block == null;
    }

}
