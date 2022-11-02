package xueli.craftgame.renderer.blocks;

import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;
import org.lwjgl.utils.vector.Vector4f;
import xueli.craftgame.block.BlockFace;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.renderer.blocks.buffer.CubeDrawer;
import xueli.craftgame.renderer.blocks.buffer.IBufferProvider;
import xueli.craftgame.world.World;
import xueli.game.vector.Vector2i;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasTextureRenderResource;

public class BlockRenderableSolid implements BlockRenderable {

	private String[] faceTextures;

	public BlockRenderableSolid(String holder) {
		this.faceTextures = new String[] { holder, holder, holder, holder, holder, holder };
	}

	public BlockRenderableSolid(String... holders) {
		if (holders.length == 6)
			this.faceTextures = holders;
		else
			this.faceTextures = new String[] { holders[0], holders[0], holders[0], holders[0], holders[0],
					holders[0] };

	}

	@Override
	public void render(int x, int y, int z, WorldRenderer renderer) {
		IBlockRenderer r = renderer.rendererCube();
		Vector2i chunkPos = World.getLocatedChunkPos(x, z);
		ChunkBuffer buf = r.getChunkBuffer(chunkPos.x, chunkPos.y);
		World world = renderer.getWorld();

		for (byte i = 0; i < BlockFace.DIRECTIONS.length; i++) {
			Vector3i dir = BlockFace.DIRECTIONS[i];
			int checkX = x + dir.x, checkY = y + dir.y, checkZ = z + dir.z;
			if (checkShouldRender(checkX, checkY, checkZ, world)) {
				drawQuad(i, x, y, z, buf, world);
			}
		}

	}

	private boolean checkShouldRender(int x, int y, int z, World world) {
		BlockType blockType = world.getBlock(x, y, z);
		if (blockType == null)
			return true;
		return !(blockType.getRenderable() instanceof BlockRenderableSolid);
	}

	@Override
	public void renderReview(WorldRenderer renderer) {
		IBlockRenderer r = renderer.rendererCube();
		ChunkBuffer buf = r.getChunkBuffer(0, 0);
		for (byte i = 0; i < BlockFace.DIRECTIONS.length; i++) {
			drawQuad(i, 0, 0, 0, buf, null);
		}

	}

	private void drawQuad(byte face, int x, int y, int z, ChunkBuffer buf, World world) {
		AtlasTextureRenderResource atlasResourceManager = buf.getWorldRenderer().getContext().getAtlasTextureResource();
		AtlasResourceHolder holder = atlasResourceManager.getHolder(IBlockRenderer.ATLAS_LOCATION, this.faceTextures[face]);
		IBufferProvider buffer = buf.getProvider(holder.textureId());

		float[] aoDegree = null;
		if (world != null) {
			aoDegree = FakeAOBaker.bake(x, y, z, face, world);
		} else {
			float faceColor = 0.85f + face / 6.0f / 1.5f;
			aoDegree = new float[] { faceColor, faceColor, faceColor, faceColor };
		}
		Vector4f c1 = new Vector4f(aoDegree[0], aoDegree[0], aoDegree[0], 1.0f),
				c2 = new Vector4f(aoDegree[1], aoDegree[1], aoDegree[1], 1.0f),
				c3 = new Vector4f(aoDegree[2], aoDegree[2], aoDegree[2], 1.0f),
				c4 = new Vector4f(aoDegree[3], aoDegree[3], aoDegree[3], 1.0f);

		switch (face) {
			case BlockFace.FRONT -> {
				CubeDrawer.drawQuadFacingFront(buffer, new Vector3f(x, y, z), holder.leftBottom(), c1,
						new Vector3f(x + 1, y, z), holder.rightBottom(), c2, new Vector3f(x, y + 1, z),
						holder.leftTop(), c3, new Vector3f(x + 1, y + 1, z), holder.rightTop(), c4);
			}
			case BlockFace.RIGHT -> {
				CubeDrawer.drawQuadFacingRight(buffer, new Vector3f(x + 1, y, z), holder.rightBottom(), c1,
						new Vector3f(x + 1, y + 1, z), holder.rightTop(), c2, new Vector3f(x + 1, y, z + 1),
						holder.leftBottom(), c3, new Vector3f(x + 1, y + 1, z + 1), holder.leftTop(), c4);
			}
			case BlockFace.BACK -> {
				CubeDrawer.drawQuadFacingBack(buffer, new Vector3f(x, y, z + 1), holder.leftBottom(), c1,
						new Vector3f(x + 1, y, z + 1), holder.rightBottom(), c2, new Vector3f(x, y + 1, z + 1),
						holder.leftTop(), c3, new Vector3f(x + 1, y + 1, z + 1), holder.rightTop(), c4);
			}
			case BlockFace.LEFT -> {
				CubeDrawer.drawQuadFacingLeft(buffer, new Vector3f(x, y, z), holder.leftBottom(), c1,
						new Vector3f(x, y + 1, z), holder.leftTop(), c2, new Vector3f(x, y, z + 1),
						holder.rightBottom(), c3, new Vector3f(x, y + 1, z + 1), holder.rightTop(), c4);
			}
			case BlockFace.TOP -> {
				CubeDrawer.drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), holder.leftBottom(), c1,
						new Vector3f(x + 1, y + 1, z), holder.leftTop(), c2, new Vector3f(x, y + 1, z + 1),
						holder.rightBottom(), c3, new Vector3f(x + 1, y + 1, z + 1), holder.rightTop(), c4);
			}
			case BlockFace.BOTTOM -> {
				CubeDrawer.drawQuadFacingBottom(buffer, new Vector3f(x, y, z), holder.leftBottom(), c1,
						new Vector3f(x + 1, y, z), holder.leftTop(), c2, new Vector3f(x, y, z + 1),
						holder.rightBottom(), c3, new Vector3f(x + 1, y, z + 1), holder.rightTop(), c4);
			}
		}

		// System.out.println(x + ", " + y + ", " + z + ", " + buf.tempCount);

	}

}
