package xueli.craftgame.renderer.blocks;

import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;
import org.lwjgl.utils.vector.Vector4f;

import xueli.craftgame.block.BlockFace;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.renderer.CubeDrawer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.world.World;
import xueli.game.resource.texture.TextureAtlasHolder;
import xueli.game.utils.WrappedFloatBuffer;
import xueli.game.vector.Vector2i;

public class BlockRenderableSolid implements BlockRenderable {

	private TextureAtlasHolder[] faceTextures;

	public BlockRenderableSolid(TextureAtlasHolder holder) {
		this.faceTextures = new TextureAtlasHolder[] { holder, holder, holder, holder, holder, holder };
	}

	public BlockRenderableSolid(TextureAtlasHolder... holders) {
		if (holders.length == 6)
			this.faceTextures = holders;
		else
			this.faceTextures = new TextureAtlasHolder[] { holders[0], holders[0], holders[0], holders[0], holders[0],
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
		WrappedFloatBuffer buffer = buf.buffer;
		TextureAtlasHolder texHolder = faceTextures[face];

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
			CubeDrawer.drawQuadFacingFront(buffer, new Vector3f(x, y, z), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y, z), texHolder.p_right_down, c2, new Vector3f(x, y + 1, z),
					texHolder.p_left_top, c3, new Vector3f(x + 1, y + 1, z), texHolder.p_right_top, c4);
		}
		case BlockFace.RIGHT -> {
			CubeDrawer.drawQuadFacingRight(buffer, new Vector3f(x + 1, y, z), texHolder.p_right_down, c1,
					new Vector3f(x + 1, y + 1, z), texHolder.p_right_top, c2, new Vector3f(x + 1, y, z + 1),
					texHolder.p_left_down, c3, new Vector3f(x + 1, y + 1, z + 1), texHolder.p_left_top, c4);
		}
		case BlockFace.BACK -> {
			CubeDrawer.drawQuadFacingBack(buffer, new Vector3f(x, y, z + 1), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y, z + 1), texHolder.p_right_down, c2, new Vector3f(x, y + 1, z + 1),
					texHolder.p_left_top, c3, new Vector3f(x + 1, y + 1, z + 1), texHolder.p_right_top, c4);
		}
		case BlockFace.LEFT -> {
			CubeDrawer.drawQuadFacingLeft(buffer, new Vector3f(x, y, z), texHolder.p_left_down, c1,
					new Vector3f(x, y + 1, z), texHolder.p_left_top, c2, new Vector3f(x, y, z + 1),
					texHolder.p_right_down, c3, new Vector3f(x, y + 1, z + 1), texHolder.p_right_top, c4);
		}
		case BlockFace.TOP -> {
			CubeDrawer.drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y + 1, z), texHolder.p_left_top, c2, new Vector3f(x, y + 1, z + 1),
					texHolder.p_right_down, c3, new Vector3f(x + 1, y + 1, z + 1), texHolder.p_right_top, c4);
		}
		case BlockFace.BOTTOM -> {
			CubeDrawer.drawQuadFacingBottom(buffer, new Vector3f(x, y, z), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y, z), texHolder.p_left_top, c2, new Vector3f(x, y, z + 1),
					texHolder.p_right_down, c3, new Vector3f(x + 1, y, z + 1), texHolder.p_right_top, c4);
		}
		}

		buf.tempCount += 6;
		// System.out.println(x + ", " + y + ", " + z + ", " + buf.tempCount);

	}

}
